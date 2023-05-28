package spring.server.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.server.dto.chat.ChatDto;
import spring.server.entity.chat.Chat;

public interface RoomCustomRepository {
    Page<Chat> findChatsByRoomId(Long roomId, Pageable pageable);
}
