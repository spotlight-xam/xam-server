package spring.server.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    private Long teamLeaderId;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    @OneToMany(mappedBy = "team")
    private List<TeamUser> teamUsers = new ArrayList<>();
}
