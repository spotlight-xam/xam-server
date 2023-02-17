package spring.server.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpResponse {

    private String email;

    private String authToken;


}
