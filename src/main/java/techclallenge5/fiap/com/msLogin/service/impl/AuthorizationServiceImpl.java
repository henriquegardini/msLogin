package techclallenge5.fiap.com.msLogin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import techclallenge5.fiap.com.msLogin.repository.UserRepository;
import techclallenge5.fiap.com.msLogin.service.AuthorizationService;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService, UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
