package spring.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class TeamMember {

    @Id @GeneratedValue
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


}