package spring.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import spring.server.dto.chat.ChatDto;
import spring.server.dto.chat.MessageType;
import spring.server.service.ChatService;

import javax.mail.Message;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long roomId;

    private String roomName;

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<RoomUser> roomUsers = new ArrayList<>();

    public Room(String roomName) {
        this.roomName = roomName;
    }


}
