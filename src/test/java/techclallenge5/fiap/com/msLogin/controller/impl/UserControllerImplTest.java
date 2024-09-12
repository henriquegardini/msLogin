package techclallenge5.fiap.com.msLogin.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;
import techclallenge5.fiap.com.msLogin.model.enums.UserRole;
import techclallenge5.fiap.com.msLogin.service.UserService;

class UserControllerImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        UserResponse user1 = new UserResponse(1L, "user1", UserRole.USER, LocalDateTime.now(),
                LocalDateTime.now());
        UserResponse user2 =
                new UserResponse(1L, "user2", UserRole.ADMIN, LocalDateTime.now(),
                        LocalDateTime.now());
        List<UserResponse> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(user1, response.getBody().get(0));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 2L;
        UserResponse user = new UserResponse(userId, "user1", UserRole.USER, LocalDateTime.now(), LocalDateTime.now());

        when(userService.findById(userId)).thenReturn(user);

        // Act
        ResponseEntity<UserResponse> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).findById(userId);
    }
}