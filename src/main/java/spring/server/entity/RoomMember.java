package spring.server.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class RoomMember {

    @Id @GeneratedValue
    private Long roomMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
