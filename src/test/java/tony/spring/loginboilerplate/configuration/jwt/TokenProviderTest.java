package tony.spring.loginboilerplate.configuration.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import tony.spring.loginboilerplate.domain.User;
import tony.spring.loginboilerplate.repository.UserRepository;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

//    @BeforeEach
//    void deleteAll() {
//        userRepository.deleteAll();
//    }

    @Test
    @DisplayName("generateToken(): 토큰 생성")
    void generateToken() {
        // given
        User testUser = User.builder()
                .email("test@gmail.com")
                .password("test")
                .build();
        userRepository.save(testUser);
        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        log.info("token: {}", token);

        // then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("validToken(): 만료된 토큰일 때 유효성 검증 실패가 정상")
    void validateToken_invalidToken() {
        // given
        Date expirationTime = new Date(new Date().getTime() - Duration.ofDays(7).toMillis());
        String token = JwtFactory.builder()
                .expiration(expirationTime)
                .build()
                .createToken(jwtProperties);
        // when
        boolean result = tokenProvider.validToken(token);
        log.info("result: {}", expirationTime);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다.")
    void getAuthentication() {
        // given
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);
        // when
        Authentication authentication = tokenProvider.getAuthentication(token);
        // then
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        assertThat(username).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("getUserId() : 토큰으로 유저 ID 를 가져올 수 있다")
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory
                .builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);
        // when
        Long userIdByToken = tokenProvider.getUserId(token);
        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}