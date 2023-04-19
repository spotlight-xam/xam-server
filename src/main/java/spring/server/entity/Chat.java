package spring.server.entity;

import javax.persistence.*;
@Entity
public class Chat {
    @Id @GeneratedValue
    private Long chatId;
    @ManyToOne
    @JoinColumn(name = "rood_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;// 메세지
    private String time; // 채팅 발송 시간

}
