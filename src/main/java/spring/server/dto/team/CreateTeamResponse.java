package spring.server.dto.team;

import lombok.Data;
import spring.server.entity.Image;

@Data
public class CreateTeamResponse {

    private Long teamId;

    private String teamName;

    private Image profileImage;
}
