package techclallenge5.fiap.com.msLogin.security;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import techclallenge5.fiap.com.msLogin.exception.UnauthorizedException;
import techclallenge5.fiap.com.msLogin.model.User;
import techclallenge5.fiap.com.msLogin.repository.UserRepository;

class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @Mock
    private PrintWriter printWriter;

    @InjectMocks
    private SecurityFilter securityFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternalValidToken() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(tokenService.validateToken("validToken")).thenReturn("validUser");
        when(userRepository.findByLogin("validUser")).thenReturn((User) userDetails);
        when(userDetails.getAuthorities()).thenReturn(null);  // Mock authorities if necessary
        when(response.getWriter()).thenReturn(printWriter);

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(tokenService).validateToken("validToken");
        verify(userRepository).findByLogin("validUser");
        verify(filterChain).doFilter(request, response);
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        assert authentication.getPrincipal().equals(userDetails);
    }

    @Test
    void testDoFilterInternalInvalidToken() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(tokenService.validateToken("invalidToken")).thenThrow(
                new UnauthorizedException("Invalid or expired token"));
        when(response.getWriter()).thenReturn(printWriter);

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(printWriter).write("Invalid or expired token");
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    void testDoFilterInternalNoToken() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);
        when(response.getWriter()).thenReturn(printWriter);

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

}
