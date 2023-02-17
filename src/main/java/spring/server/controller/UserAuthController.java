package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.login.EmailAuthRequest;
import spring.server.dto.login.UserLoginRequest;
import spring.server.dto.login.UserSignUpResponse;
import spring.server.dto.login.UserSignupRequest;
import spring.server.service.UserAuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok().body(userAuthService.login(userLoginRequest));
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@ModelAttribute EmailAuthRequest emailAuthRequest){
        log.info("컨펌이메일");
        userAuthService.confirmEmail(emailAuthRequest);
        return ResponseEntity.ok().body("인증 완료");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signup(@RequestBody UserSignupRequest userSignupRequest){
        log.info("signup controller 실행");
        UserSignUpResponse signupResponse = userAuthService.signup(userSignupRequest);
        return ResponseEntity.ok().body(signupResponse);

    }
}
