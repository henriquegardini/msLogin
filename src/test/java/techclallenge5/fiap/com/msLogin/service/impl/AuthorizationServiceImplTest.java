package techclallenge5.fiap.com.msLogin.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import techclallenge5.fiap.com.msLogin.repository.UserRepository;

class AuthorizationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Arrange
        String username = "testuser";
        UserDetails userDetails = mock(UserDetails.class);
        when(userRepository.findByLogin(username)).thenReturn(userDetails);

        // Act
        UserDetails result = authorizationService.loadUserByUsername(username);

        // Assert
        assertEquals(userDetails, result);
        verify(userRepository, times(1)).findByLogin(username);
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        // Arrange
        String username = "nonexistentuser";
        when(userRepository.findByLogin(username)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByLogin(username);
    }
}