package spring.server.dto.chat;

import lombok.Data;
import spring.server.dto.member.ChatRoomMemberInfo;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRoomResponse {

    private Long roomId;

    private ChatRoomMemberInfo inviter;

    private List<ChatRoomMemberInfo> members = new ArrayList<>();

    public CreateRoomResponse(Long roomId, ChatRoomMemberInfo inviter, List<ChatRoomMemberInfo> members) {
        this.roomId = roomId;
        this.inviter = inviter;
        this.members = members;
    }
}
