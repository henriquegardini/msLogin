package techclallenge5.fiap.com.msLogin.controller;

import org.springframework.http.ResponseEntity;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.response.AuthResponse;

public interface AuthController {
    ResponseEntity<AuthResponse> login(UserAuthRequest userAuthRequest);
}
