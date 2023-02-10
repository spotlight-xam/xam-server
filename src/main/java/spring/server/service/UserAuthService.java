package spring.server.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import spring.server.entity.dto.login.UserLoginRequest;
import spring.server.repository.UserAuthRepository;
import spring.server.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private UserAuthRepository userAuthRepository;

    public String login(UserLoginRequest userLoginRequest){

        return JwtUtil.createJwt(userLoginRequest.getEmail());
    }

    public void signup(String email, String password) {
        //생성
        if(userAuthRepository.findByEmail(email).isPresent()){

        }

    }
}
