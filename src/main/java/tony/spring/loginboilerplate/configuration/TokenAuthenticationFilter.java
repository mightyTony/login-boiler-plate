package tony.spring.loginboilerplate.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tony.spring.loginboilerplate.configuration.jwt.TokenProvider;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더의 Authorization 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정
        if(tokenProvider.validToken(token)) {
            // 토큰이 유효하면 토큰으로부터 인증 정보를 가져와서 SecurityContext에 설정
            Authentication authentication = tokenProvider.getAuthentication(token);
            // SecurityContext에 인증 정보를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 넘어가기
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        // 헤더에서 토큰을 가져오는 메서드
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            // Bearer 다음에 오는 토큰을 가져온다.
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
