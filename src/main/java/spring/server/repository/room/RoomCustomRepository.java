package spring.server.repository.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.server.dto.chat.ChatDto;
import spring.server.entity.Room;
import spring.server.entity.chat.Chat;

public interface RoomCustomRepository {
    Page<Chat> findChatsByRoomId(Long roomId, Pageable pageable);

    Page<Room> findByTeamId(Long teamId, Pageable pageable);
}
