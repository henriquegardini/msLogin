package techclallenge5.fiap.com.msLogin.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;

public interface UserController {

    ResponseEntity<List<UserResponse>> getAllUsers();
}
