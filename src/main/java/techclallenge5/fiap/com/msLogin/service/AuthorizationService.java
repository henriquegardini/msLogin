package techclallenge5.fiap.com.msLogin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
