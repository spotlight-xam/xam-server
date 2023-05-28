package spring.server.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.server.entity.chat.ChatImage;
import spring.server.entity.chat.ChatText;

import java.util.List;

public interface ChatTextRepository extends JpaRepository<ChatText, Long> {

    @Query("select ct from ChatText ct where ct.id in :chatIds")
    List<ChatText> findAllByIdIn(@Param("chatIds") List<Long> chatIds);
}
