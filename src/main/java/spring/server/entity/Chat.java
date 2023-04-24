package spring.server.entity;

import javax.persistence.*;
@Entity
public class Chat {
    @Id @GeneratedValue
    private Long chatId;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String message;// 메세지
    private String time; // 채팅 발송 시간

}
