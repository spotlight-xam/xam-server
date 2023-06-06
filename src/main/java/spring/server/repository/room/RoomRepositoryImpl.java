package spring.server.repository.room;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import spring.server.entity.QRoom;
import spring.server.entity.QTeam;
import spring.server.entity.Room;
import spring.server.entity.chat.Chat;
import spring.server.entity.chat.QChat;

import javax.persistence.EntityManager;

import java.util.List;

import static spring.server.entity.QRoom.room;
import static spring.server.entity.QTeam.team;
import static spring.server.entity.chat.QChat.chat;

public class RoomRepositoryImpl implements RoomCustomRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public RoomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Chat> findChatsByRoomId(Long roomId, Pageable pageable) {
        QueryResults<Chat> chats = jpaQueryFactory
                .selectFrom(chat)
                .join(chat.room, room)
                .where(room.id.eq(roomId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Chat> results = chats.getResults();

        return new PageImpl<>(results, pageable, chats.getTotal());
    }

    @Override
    public Page<Room> findByTeamId(Long teamId, Pageable pageable) {
        List<Room> rooms = jpaQueryFactory
                .select(room)
                .from(team)
                .join(team.rooms, room)
                .where(team.id.eq(teamId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQueryFactory
                .selectFrom(room)
                .where(team.id.eq(teamId))
                .fetchCount();

        return new PageImpl<>(rooms, pageable, count);
    }


}
