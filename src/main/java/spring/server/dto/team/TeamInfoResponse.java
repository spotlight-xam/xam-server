package spring.server.dto.team;

import lombok.Data;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamInfoResponse {

    private List<Member> memberList = new ArrayList<>();

    private String teamName;

    private Member leader;

}