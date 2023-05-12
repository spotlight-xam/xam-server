package spring.server.dto.team;

import lombok.Data;
import spring.server.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamInfoResponse {

    private List<String> memberList = new ArrayList<>();

    private String teamName;

    private Member leader;

    public void addMember(String username) {
        memberList.add(username);
    }

}