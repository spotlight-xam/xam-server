package spring.server.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.chat.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
