package tony.spring.loginboilerplate.configuration.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tony.spring.loginboilerplate.configuration.jwt.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
