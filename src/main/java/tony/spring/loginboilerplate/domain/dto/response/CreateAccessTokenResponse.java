package tony.spring.loginboilerplate.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String accessToken;
}
