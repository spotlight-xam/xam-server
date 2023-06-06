package spring.server.dto.chat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.server.dto.member.MemberDto;
import spring.server.dto.member.MemberProfileResponse;
import spring.server.entity.Image;

@Getter
@Setter
@NoArgsConstructor
public class JoinRoomDto {

    private Long roomId;

    private Image roomProfileImage;
}
