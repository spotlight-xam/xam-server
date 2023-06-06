package spring.server.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.server.entity.Room;

@Getter
@Setter
@NoArgsConstructor
public class RoomDto {
    private Long roomId;

    private String roomName;

    public RoomDto(Room room) {
        this.roomId = room.getId();
        this.roomName = room.getRoomName();
    }
}
