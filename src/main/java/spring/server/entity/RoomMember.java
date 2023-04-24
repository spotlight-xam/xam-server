package spring.server.entity;

import javax.persistence.*;

@Entity
public class RoomMember {

    @Id @GeneratedValue
    private Long roomMemberId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
