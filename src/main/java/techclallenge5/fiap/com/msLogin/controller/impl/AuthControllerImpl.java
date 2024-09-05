package techclallenge5.fiap.com.msLogin.controller.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techclallenge5.fiap.com.msLogin.controller.AuthController;
import techclallenge5.fiap.com.msLogin.dto.UserDto;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.dto.response.AuthResponse;
import techclallenge5.fiap.com.msLogin.security.TokenService;
import techclallenge5.fiap.com.msLogin.service.UserService;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthControllerImpl implements AuthController {


    private AuthenticationManager authenticationManager;

    private UserService userService;

    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserAuthRequest data) {
        UserDto user = userService.authenticate(data);
        var auth = new UsernamePasswordAuthenticationToken(user.login(), data.password());
        authenticationManager.authenticate(auth);
        var token = tokenService.generateToken(user.login());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequest data) {
        userService.registerUser(data);
        return ResponseEntity.ok().build();
    }
}