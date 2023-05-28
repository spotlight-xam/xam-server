package spring.server.repository.chat;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import spring.server.dto.chat.ChatDto;
import spring.server.entity.chat.Chat;
import spring.server.entity.chat.QChat;

import javax.persistence.EntityManager;

import java.util.List;

import static spring.server.entity.QRoom.room;
import static spring.server.entity.chat.QChat.chat;

public class RoomRepositoryImpl implements RoomCustomRepository{

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public RoomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Chat> findChatsByRoomId(Long roomId, Pageable pageable) {
        QueryResults<Chat> chats = jpaQueryFactory
                .selectFrom(QChat.chat)
                .join(QChat.chat.room, room)
                .where(room.id.eq(roomId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Chat> results = chats.getResults();


        return new PageImpl<>(results, pageable, chats.getTotal());
    }


}
