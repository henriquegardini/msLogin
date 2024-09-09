package techclallenge5.fiap.com.msLogin.controller.impl;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import techclallenge5.fiap.com.msLogin.controller.UserController;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;
import techclallenge5.fiap.com.msLogin.service.UserService;

@RestController
@AllArgsConstructor
public class UserControllerImpl implements UserController {


    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(UUID id) {
        UserResponse user = service.findById(id);
        return ResponseEntity.ok(user);
    }

}
