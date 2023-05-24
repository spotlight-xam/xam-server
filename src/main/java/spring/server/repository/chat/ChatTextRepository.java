package spring.server.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.chat.ChatText;

public interface ChatTextRepository extends JpaRepository<ChatText, Long> {
}
