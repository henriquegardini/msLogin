package techclallenge5.fiap.com.msLogin.model.enums;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    public static UserRole greaterRole(Collection<? extends GrantedAuthority> roles) {
        return roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN")) ? ADMIN : USER;
    }

}
