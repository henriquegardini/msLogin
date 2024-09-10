package techclallenge5.fiap.com.msLogin.dto;

import techclallenge5.fiap.com.msLogin.model.enums.UserRole;

public record UserDto(String login, String password, UserRole role) {
}
