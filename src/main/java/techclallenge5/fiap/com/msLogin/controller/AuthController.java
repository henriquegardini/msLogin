package techclallenge5.fiap.com.msLogin.controller;

import org.springframework.http.ResponseEntity;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.model.User;

public interface AuthController {
    ResponseEntity register(UserRequest user);

    ResponseEntity login(UserAuthRequest userAuthRequest);
}
