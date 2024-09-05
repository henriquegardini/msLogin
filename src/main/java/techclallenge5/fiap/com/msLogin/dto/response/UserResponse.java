package techclallenge5.fiap.com.msLogin.dto.response;

import java.time.LocalDateTime;
import techclallenge5.fiap.com.msLogin.model.enums.UserRole;

public record UserResponse (String id, String login, UserRole role, LocalDateTime createdDate, LocalDateTime updatedDate) {
}
