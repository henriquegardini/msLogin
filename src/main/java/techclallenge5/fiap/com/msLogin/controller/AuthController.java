package techclallenge5.fiap.com.msLogin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.dto.response.AuthResponse;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;

@RequestMapping("/auth")
@Tag(name = "Auth")
public interface AuthController {

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso")
    ResponseEntity<AuthResponse> login(@RequestBody @Valid UserAuthRequest userAuthRequest);


    @PostMapping("/register")
    @Operation(summary = "Register")
    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso")
    ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest data);

    @PostMapping("/validate")
    @Operation(summary = "Validate Token")
    @ApiResponse(responseCode = "200", description = "Token validado com sucesso")
    ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token);
}
