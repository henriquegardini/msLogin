package techclallenge5.fiap.com.msLogin.dto.request;

import techclallenge5.fiap.com.msLogin.model.enums.UserRole;

public record UserRequest(String login, String password, UserRole role) {
}
