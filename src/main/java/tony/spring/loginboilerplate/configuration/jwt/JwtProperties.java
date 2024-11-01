package tony.spring.loginboilerplate.configuration.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter@Getter
@ConfigurationProperties("jwt") // yml 안의 jwt에 대한 설정을 받아온다.
@Component
public class JwtProperties {

    private String issuer;
    private String secretKey;
}
