package techclallenge5.fiap.com.msLogin.mapper;

import techclallenge5.fiap.com.msLogin.dto.UserDto;
import techclallenge5.fiap.com.msLogin.dto.response.UserResponse;
import techclallenge5.fiap.com.msLogin.model.User;
import techclallenge5.fiap.com.msLogin.model.enums.UserRole;

public interface UserMapper {

    static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getLogin(), user.getRole(), user.getToken(), user.getCreatedDate(),
                user.getUpdatedDate());
    }

    static UserDto toUserDto(String login, String password, UserRole role) {
        return new UserDto(login, password, role);
    }
}
