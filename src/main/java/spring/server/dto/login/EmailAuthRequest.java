package spring.server.dto.login;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailAuthRequest {

    private String email;

    private String authToken;

}
