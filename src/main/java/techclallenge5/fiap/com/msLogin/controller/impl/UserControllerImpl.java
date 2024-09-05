package techclallenge5.fiap.com.msLogin.controller.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techclallenge5.fiap.com.msLogin.controller.UserController;
import techclallenge5.fiap.com.msLogin.model.User;
import techclallenge5.fiap.com.msLogin.repository.UserRepository;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserControllerImpl implements UserController {


    private UserRepository repository;

    @GetMapping
    public ResponseEntity getAllUsers(){
        List<User> users = this.repository.findAll();

        return ResponseEntity.ok(users);
    }

}
