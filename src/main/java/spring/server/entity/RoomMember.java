package spring.server.entity;

import javax.persistence.*;

@Entity
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
