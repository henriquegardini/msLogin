package techclallenge5.fiap.com.msLogin.security;

import static techclallenge5.fiap.com.msLogin.model.enums.UserRole.ADMIN;
import static techclallenge5.fiap.com.msLogin.model.enums.UserRole.USER;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import techclallenge5.fiap.com.msLogin.exception.TokenCreationException;
import techclallenge5.fiap.com.msLogin.exception.UnauthorizedException;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(String login, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(login)
                    .withClaim("role", role)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new TokenCreationException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decodedJWT = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token);

            String role = decodedJWT.getClaim("role").asString();

            if (!(role.equals(USER.name()) || role.equals(ADMIN.name()))) {
                throw new UnauthorizedException("Invalid role in token");
            }

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new UnauthorizedException("Invalid or expired token");
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
