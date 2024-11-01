package tony.spring.loginboilerplate.domain.dto.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String email;
    private String password;
}
