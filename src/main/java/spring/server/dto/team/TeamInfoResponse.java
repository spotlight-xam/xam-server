package spring.server.dto.team;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestHeader;
import spring.server.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamInfoResponse {

    private List<User> userList = new ArrayList<>();

    private String teamName;

    private User leader;

}