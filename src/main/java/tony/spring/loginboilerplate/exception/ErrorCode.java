package tony.spring.loginboilerplate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNEXPECTED_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
