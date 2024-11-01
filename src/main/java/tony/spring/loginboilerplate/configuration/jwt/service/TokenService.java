package tony.spring.loginboilerplate.configuration.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.spring.loginboilerplate.common.Constant;
import tony.spring.loginboilerplate.configuration.jwt.TokenProvider;
import tony.spring.loginboilerplate.configuration.jwt.domain.RefreshToken;
import tony.spring.loginboilerplate.domain.User;
import tony.spring.loginboilerplate.exception.AuthException;
import tony.spring.loginboilerplate.exception.ErrorCode;
import tony.spring.loginboilerplate.service.UserService;

import java.sql.Ref;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createAccessToken(String refreshToken) {
        // 토큰 유효성 검사
        if(!tokenProvider.validToken(refreshToken)) {
            throw new AuthException(ErrorCode.INVALID_TOKEN);
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Constant.ACCESS_TOKEN_EXPIRE_TIME);
    }
}
