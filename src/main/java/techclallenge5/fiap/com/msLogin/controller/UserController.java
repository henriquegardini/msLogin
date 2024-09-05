package techclallenge5.fiap.com.msLogin.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import techclallenge5.fiap.com.msLogin.model.User;

public interface UserController {

    ResponseEntity<List<User>> getAllUsers();
}
