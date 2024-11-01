package tony.spring.loginboilerplate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private ErrorCode errorCode;
    private String message;
}