package techclallenge5.fiap.com.msLogin.controller.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import techclallenge5.fiap.com.msLogin.controller.AuthController;
import techclallenge5.fiap.com.msLogin.dto.UserDto;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.dto.response.AuthResponse;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;
import techclallenge5.fiap.com.msLogin.security.TokenService;
import techclallenge5.fiap.com.msLogin.service.UserService;

@RestController
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {


    private AuthenticationManager authenticationManager;

    private UserService userService;

    private TokenService tokenService;

    public ResponseEntity<AuthResponse> login(UserAuthRequest data) {
        UserDto user = userService.authenticate(data);
        var auth = new UsernamePasswordAuthenticationToken(user.login(), data.password());
        authenticationManager.authenticate(auth);
        var token = tokenService.generateToken(user.login(), user.role().name());
        return ResponseEntity.ok(new AuthResponse(token));
    }


    public ResponseEntity<UserResponse> register(UserRequest data) {
        UserResponse user = userService.registerUser(data);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Void> validateToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        tokenService.validateToken(token);
        return ResponseEntity.ok().build();
    }
}