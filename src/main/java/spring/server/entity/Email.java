package spring.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Email {

    @Id @GeneratedValue
    private Long id;

    private String email;

    private String authToken;

    private Boolean expired;

    private LocalDateTime expireDate;

    private static final Long MAX_EXPIRE_TIME = 30L;

    @Builder
    public Email(String email, String authToken, Boolean expired) {
        this.email = email;
        this.authToken = authToken;
        this.expired = expired;
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void useToken(){
        this.expired=true;
    }
}
