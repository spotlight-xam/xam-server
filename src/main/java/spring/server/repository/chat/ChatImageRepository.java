package spring.server.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.chat.ChatImage;

public interface ChatImageRepository extends JpaRepository<ChatImage, Long> {
}
