package spring.server.repository.email;

import spring.server.entity.Email;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailCustomRepository {
    Optional <Email> findValidAuthByEmail(String email, String authToken, LocalDateTime localDateTime);
}
