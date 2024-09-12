package techclallenge5.fiap.com.msLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techclallenge5.fiap.com.msLogin.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
}
