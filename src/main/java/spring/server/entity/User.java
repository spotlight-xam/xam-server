package spring.server.entity;


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
@Table(name = "MY_USER")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String username;

    private String password;

    private String refreshToken;

    private Boolean emailAuth;

    @OneToMany(mappedBy = "writer")
    private List<Feed> feedList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userList")
    private TeamUser userTeam;

    @Builder
    public User(String email, String username, String password, String refreshToken, Boolean emailAuth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
        this.emailAuth = emailAuth;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }
}
