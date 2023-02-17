package spring.server.repository.email;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long>, EmailCustomRepository {
}
