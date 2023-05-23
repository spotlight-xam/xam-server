package spring.server.dto.chat;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRoomRequest {

    private String roomName;

    private List<String> usernames = new ArrayList<>();

}
