package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.login.EmailAuthRequest;
import spring.server.dto.login.UserLoginRequest;
import spring.server.dto.login.UserSignUpResponse;
import spring.server.dto.login.UserSignupRequest;
import spring.server.service.UserAuthService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @GetMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {

        log.info("서비스 시작");
        final String login = userAuthService.login(userLoginRequest);
        log.info("서비스 끝");
        return ResponseEntity.ok().body(login);
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@ModelAttribute EmailAuthRequest emailAuthRequest){
        log.info("컨펌이메일");
        userAuthService.confirmEmail(emailAuthRequest);
        return ResponseEntity.ok().body("인증 완료");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signup(@RequestBody UserSignupRequest userSignupRequest) throws MessagingException {
        log.info("signup controller 실행");
        UserSignUpResponse signupResponse = userAuthService.signup(userSignupRequest);
        return ResponseEntity.ok().body(signupResponse);
    }
}
