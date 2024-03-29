package spring.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.server.entity.chat.Chat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String roomName;

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public void updateName(String newName) {
        this.roomName = newName;
    }
}
