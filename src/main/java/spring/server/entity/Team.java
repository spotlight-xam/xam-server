package spring.server.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<Room> roomList = new ArrayList<>();

    public Team(String teamName) {
    @OneToOne
    private Image profileImage;

    @OneToMany
    private List<Room> rooms = new ArrayList<>();

    @Builder
    public Team(String teamName, Image profileImage) {
        this.teamName = teamName;
        this.profileImage = profileImage;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    public void addTeamMember(TeamMember teamMember) {
        this.teamMembers.add(teamMember);
    }

}
