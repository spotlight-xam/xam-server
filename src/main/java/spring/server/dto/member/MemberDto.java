package spring.server.dto.member;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.server.entity.Image;
import spring.server.entity.Member;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String username;
    private Image profileImage;

    @QueryProjection
    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.profileImage = member.getProfileImage();
    }

}
