package techclallenge5.fiap.com.msLogin.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import techclallenge5.fiap.com.msLogin.exception.TokenCreationException;
import techclallenge5.fiap.com.msLogin.exception.UnauthorizedException;

class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private String secret = "test-secret";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject the secret value into the tokenService
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    void testGenerateTokenSuccess() {
        String login = "testUser";
        String role = "USER";

        String token = tokenService.generateToken(login, role);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // Basic check for JWT structure
    }

    @Test
    void testGenerateTokenThrowsTokenCreationException() {
        TokenService faultyService = new TokenService(null);
        ReflectionTestUtils.setField(faultyService, "secret", secret);

        try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
            mockedJWT.when(() -> JWT.create()).thenThrow(JWTCreationException.class);

            assertThrows(TokenCreationException.class, () -> {
                faultyService.generateToken("invalidUser", "USER");
            });
        }
    }

    @Test
    void testValidateTokenSuccess() {
        String login = "testUser";
        String role = "USER";
        String token = tokenService.generateToken(login, role);

        String validatedLogin = tokenService.validateToken(token);

        assertEquals(login, validatedLogin);
    }

    @Test
    void testValidateTokenThrowsUnauthorizedException() {
        String invalidToken = JWT.create().sign(Algorithm.HMAC256("invalid-secret"));

        assertThrows(UnauthorizedException.class, () -> {
            tokenService.validateToken(invalidToken);
        });
    }

    @Test
    void testValidateTokenExpiredThrowsUnauthorizedException() {
        String expiredToken = JWT.create()
                .withIssuer("auth-api")
                .withSubject("testUser")
                .withExpiresAt(Instant.now().minusSeconds(3600)) // Token expired 1 hour ago
                .sign(Algorithm.HMAC256(secret));

        assertThrows(UnauthorizedException.class, () -> {
            tokenService.validateToken(expiredToken);
        });
    }
}