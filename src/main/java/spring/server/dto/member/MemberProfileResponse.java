package spring.server.dto.member;

import lombok.Data;
import spring.server.entity.Feed;
import spring.server.entity.Image;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberProfileResponse {

    private Long memberId;

    private String username;

    private Image profileImage;

    private List<Feed> feedList = new ArrayList<>();

    public MemberProfileResponse(Member member) {
        this.memberId = member.getId();
        this.username = member.getUsername();
        this.profileImage = member.getProfileImage();
        this.feedList = member.getFeedList();
    }
}
