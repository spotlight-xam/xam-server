package spring.server.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String username;

    private String password;

    private String refreshToken;

    private Boolean emailAuth;

    private String roles;

    @OneToMany
    private List<Feed> feedList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public Member(String email, String username, String password, String refreshToken, Boolean emailAuth, String roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
        this.emailAuth = emailAuth;
        this.roles = roles;
    }

    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }

    public void addTeamMember(TeamMember teamMember) {
        this.teamMembers.add(teamMember);
    }
}
