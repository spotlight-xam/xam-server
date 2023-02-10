package spring.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.server.entity.dto.login.UserLoginRequest;
import spring.server.entity.dto.login.UserSignupRequest;
import spring.server.result.ResultResponse;
import spring.server.service.UserAuthService;

@RestController
@RequiredArgsConstructor
public class UserAuthController {

//    private final UserAuthService userAuthService;

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){
//        return ResponseEntity.ok().body(userAuthService.login(userLoginRequest));
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<ResultResponse> signup(UserSignupRequest userSignupRequest){
//        String email = userSignupRequest.getEmail();
//        String password = userSignupRequest.getPassword();
//
//        userAuthService.signup(email, password);
//
//    }
}
