package techclallenge5.fiap.com.msLogin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;

@RequestMapping("users")
@Tag(name = "Users")
public interface UserController {

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserResponse>> getAllUsers();

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<UserResponse> getUserById(@PathVariable UUID id);
}
