package tony.spring.loginboilerplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tony.spring.loginboilerplate.configuration.jwt.service.TokenService;
import tony.spring.loginboilerplate.domain.dto.request.CreateAccessTokenRequest;
import tony.spring.loginboilerplate.domain.dto.response.CreateAccessTokenResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResponse> createAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createAccessToken(request.getRefreshToken());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
