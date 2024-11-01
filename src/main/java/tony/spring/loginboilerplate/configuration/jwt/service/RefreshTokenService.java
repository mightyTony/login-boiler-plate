package tony.spring.loginboilerplate.configuration.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tony.spring.loginboilerplate.configuration.jwt.domain.RefreshToken;
import tony.spring.loginboilerplate.configuration.jwt.repository.RefreshTokenRepository;
import tony.spring.loginboilerplate.exception.AuthException;
import tony.spring.loginboilerplate.exception.ErrorCode;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthException(ErrorCode.NOT_FOUND_REFRESH_TOKEN));
    }
}
