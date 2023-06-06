package spring.server.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.server.entity.Image;

@Data
@AllArgsConstructor
public class CreateTeamRequest {

    private String teamName;

    private Image profileImage;

}
