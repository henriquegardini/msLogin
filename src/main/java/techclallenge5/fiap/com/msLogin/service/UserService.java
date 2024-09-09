package techclallenge5.fiap.com.msLogin.service;

import java.util.List;
import java.util.UUID;
import techclallenge5.fiap.com.msLogin.dto.UserDto;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse registerUser(UserRequest userRequest);

    UserDto authenticate(UserAuthRequest userAuthRequest);

    UserResponse findById(UUID id);
}
