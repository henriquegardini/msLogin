package techclallenge5.fiap.com.msLogin.dto.response;

import java.time.LocalDateTime;
import techclallenge5.fiap.com.msLogin.model.enums.UserRole;

public record UserResponse (Long id, String login, UserRole role, String token, LocalDateTime createdDate, LocalDateTime updatedDate) {
}
