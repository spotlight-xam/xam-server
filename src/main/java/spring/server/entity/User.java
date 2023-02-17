package spring.server.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MY_USER")
public class User {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String username;

    private String password;

    private String refreshToken;

    private Boolean emailAuth;

    @Builder
    public User(String email, String username, String password, String refreshToken, Boolean emailAuth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
        this.emailAuth = emailAuth;
    }

    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }
}
