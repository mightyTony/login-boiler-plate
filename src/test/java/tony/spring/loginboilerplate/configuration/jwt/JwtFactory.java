package tony.spring.loginboilerplate.configuration.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.emptyMap;


@Getter
public class JwtFactory {
    private String subject = "test@gmail.com";
    private Date issueAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap();

    @Builder
    public JwtFactory(String subject, Date issueAt, Date expiration, Map<String, Object> claims) {
        this.subject = subject != null? subject : this.subject;
        this.issueAt = issueAt != null? issueAt : this.issueAt;
        this.expiration = expiration != null? expiration : this.expiration;
        this.claims = claims != null? claims : this.claims;
    }

    public static JwtFactory withDefault() {
        return JwtFactory.builder().build();
    }

    // jwt library
    public String createToken(JwtProperties jwtProperties) {
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam("typ", "JWT")
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
