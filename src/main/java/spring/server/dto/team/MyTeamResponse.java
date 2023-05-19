package spring.server.dto.team;

import lombok.Data;
import spring.server.entity.Team;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyTeamResponse {

    public List<Team> myTeamList = new ArrayList<>();
}
