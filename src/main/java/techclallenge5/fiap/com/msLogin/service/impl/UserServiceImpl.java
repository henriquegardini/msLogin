package techclallenge5.fiap.com.msLogin.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import techclallenge5.fiap.com.msLogin.dto.UserDto;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;
import techclallenge5.fiap.com.msLogin.exception.InvalidPasswordException;
import techclallenge5.fiap.com.msLogin.exception.UserNotFoundException;
import techclallenge5.fiap.com.msLogin.mapper.UserMapper;
import techclallenge5.fiap.com.msLogin.model.User;
import techclallenge5.fiap.com.msLogin.repository.UserRepository;
import techclallenge5.fiap.com.msLogin.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public List<UserResponse> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void registerUser(UserRequest data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new IllegalArgumentException("User with this login already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        repository.save(newUser);
    }

    @Override
    public UserDto authenticate(UserAuthRequest data) {
        UserDetails user = repository.findByLogin(data.login());

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return UserMapper.toUserDto(user.getUsername(), user.getPassword());
    }
}
