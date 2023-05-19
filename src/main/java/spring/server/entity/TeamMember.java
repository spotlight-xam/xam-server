package spring.server.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class TeamMember {

    @Id @GeneratedValue
    private Long teamMemberId;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
    }
}