package spring.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.server.entity.chat.Chat;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;

@Entity
@Getter
@NoArgsConstructor
public class JoinRoom {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public JoinRoom(Room room, Member member, Chat chat) {
        this.room = room;
        this.member = member;
    }
}
