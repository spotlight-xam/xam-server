package spring.server.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;
import spring.server.entity.Image;
import spring.server.entity.Member;

@Data
@NoArgsConstructor
public class ChatRoomMemberInfo {

    private String username;

    private Image profileImage;

    public ChatRoomMemberInfo(Member member) {
        this.username = member.getUsername();
        this.profileImage = member.getProfileImage();
    }
}
