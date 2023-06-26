package spring.server.dto.member;

import lombok.Data;
import spring.server.entity.Feed;
import spring.server.entity.Image;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class MemberProfileResponse {

    private Long memberId;

    private String username;

    private Image profileImage;

    private List<Feed> feedList = new ArrayList<>();

    public MemberProfileResponse(Optional<Member> member) {
        this.memberId = member.get().getId();
        this.username = member.get().getUsername();
        this.profileImage = member.get().getProfileImage();
        this.feedList = member.get().getFeedList();
    }
}
