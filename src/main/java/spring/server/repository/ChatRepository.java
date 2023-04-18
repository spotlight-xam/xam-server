package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
