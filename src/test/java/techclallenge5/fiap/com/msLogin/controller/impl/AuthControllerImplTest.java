package techclallenge5.fiap.com.msLogin.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import techclallenge5.fiap.com.msLogin.dto.UserDto;
import techclallenge5.fiap.com.msLogin.dto.request.UserAuthRequest;
import techclallenge5.fiap.com.msLogin.dto.request.UserRequest;
import techclallenge5.fiap.com.msLogin.dto.response.AuthResponse;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;
import techclallenge5.fiap.com.msLogin.model.enums.UserRole;
import techclallenge5.fiap.com.msLogin.security.TokenService;
import techclallenge5.fiap.com.msLogin.service.UserService;

public class AuthControllerImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthControllerImpl authController;

    public AuthControllerImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginShouldReturnAuthResponseWhenCredentialsAreCorrect() {
        // Arrange
        UserAuthRequest authRequest = new UserAuthRequest("user", "password");
        UserDto userDto = new UserDto("user", "password", UserRole.USER);
        String token = "jwt-token";

        when(userService.authenticate(authRequest)).thenReturn(userDto);
        when(tokenService.generateToken(userDto.login(), userDto.role().name())).thenReturn(token);

        // Act
        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody().token());
    }

    @Test
    void registerShouldReturnUserResponseWhenRegistrationIsSuccessful() {
        // Arrange
        UserRequest userRequest = new UserRequest("user", "password", UserRole.USER);
        UserResponse userResponse = new UserResponse(1L, "user", UserRole.USER, null, null);

        when(userService.registerUser(userRequest)).thenReturn(userResponse);

        // Act
        ResponseEntity<UserResponse> response = authController.register(userRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }
}