package spring.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class TeamUser {

    @Id @GeneratedValue
    private Long teamUserId;

    @OneToMany(mappedBy = "teamUser")
    private List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "teamUser")
    private List<Team> teamList = new ArrayList<>();


}