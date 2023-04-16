package spring.server.dto.team;

import lombok.Data;
import spring.server.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddUserRequest {

    private Long teamId;

    private List<User> userList = new ArrayList<>();


}
