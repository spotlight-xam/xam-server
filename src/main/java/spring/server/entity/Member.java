package spring.server.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "writer")
    private List<Feed> feedList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "member")
    private List<RoomMember> roomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public Member(String email, String username, String password, String refreshToken, Boolean emailAuth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
        this.emailAuth = emailAuth;
    }

    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }
}