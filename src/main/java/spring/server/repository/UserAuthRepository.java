package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.User;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
}
