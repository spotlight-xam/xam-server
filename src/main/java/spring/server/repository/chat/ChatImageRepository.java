package spring.server.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.server.entity.chat.ChatImage;

import java.util.Collection;
import java.util.List;

public interface ChatImageRepository extends JpaRepository<ChatImage, Long> {
    @Query("select ci from ChatImage ci where ci.id in :chatIds")
    List<ChatImage> findAllByIdIn(@Param("chatIds") List<Long> chatIds);
}
