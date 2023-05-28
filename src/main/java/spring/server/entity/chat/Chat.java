package spring.server.entity.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.server.entity.Member;
import spring.server.entity.Room;

import javax.persistence.*;
@Entity
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String time; // 채팅 발송 시간
    @Column(insertable = false, updatable = false)
    private String dtype;

    public Chat(Room room, Member member) {
        this.room = room;
        this.member = member;
    }

    @Transient
    public void setDtype() {
        this.dtype = getClass().getAnnotation(DiscriminatorValue.class).value();
    }

}
