package techclallenge5.fiap.com.msLogin.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
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
import techclallenge5.fiap.com.msLogin.model.enums.UserRole;
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
                .toList();
    }

    @Override
    public UserResponse registerUser(UserRequest data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new IllegalArgumentException("User with this login already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        repository.save(newUser);

        return UserMapper.toUserResponse(newUser);
    }

    @Override
    public UserDto authenticate(UserAuthRequest data) {
        User user = this.findByLogin(data.login());

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return UserMapper.toUserDto(user.getUsername(), user.getPassword(),
                UserRole.greaterRole(user.getAuthorities()));
    }

    @Override
    public UserResponse findById(Long id) {
        User user = repository.findById(id.toString())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserMapper.toUserResponse(user);
    }

    @Override
    public User findByLogin(String login) {
        return Optional.of(repository.findByLogin(login))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }
}
