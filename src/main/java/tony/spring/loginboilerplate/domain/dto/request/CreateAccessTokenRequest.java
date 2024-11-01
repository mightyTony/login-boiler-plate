package tony.spring.loginboilerplate.domain.dto.request;

import lombok.Data;

@Data
public class CreateAccessTokenRequest {
    private String refreshToken;
}
