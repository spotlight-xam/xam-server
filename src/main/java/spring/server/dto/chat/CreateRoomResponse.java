package spring.server.dto.chat;

import lombok.Data;
import spring.server.dto.member.ChatRoomMemberInfo;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRoomResponse {

    private Long roomId;
    private String roomName;
    public CreateRoomResponse(Long roomId, String roomName) {
        this.roomId = roomId;
    }
}
