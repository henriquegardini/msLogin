package techclallenge5.fiap.com.msLogin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;

@RequestMapping("users")
@Tag(name = "Users")
public interface UserController {

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso")
    ResponseEntity<List<UserResponse>> getAllUsers();
}
