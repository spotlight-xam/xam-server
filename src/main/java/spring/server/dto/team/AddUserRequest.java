package spring.server.dto.team;

import lombok.Data;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddUserRequest {

    private Long teamId;

    private List<Member> memberList = new ArrayList<>();


}
