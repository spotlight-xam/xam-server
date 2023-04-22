package spring.server.dto.chat;

import lombok.Data;
import spring.server.entity.Room;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomlistResponse {

    private List<Room> roomList = new ArrayList<>();


}
