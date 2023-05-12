package spring.server.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CreateTeamRequest {
    private String teamName;


}
