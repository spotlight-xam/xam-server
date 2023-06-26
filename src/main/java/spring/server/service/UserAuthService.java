package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.server.dto.login.EmailAuthRequest;
import spring.server.dto.login.UserSignUpResponse;
import spring.server.entity.Email;
import spring.server.entity.Member;
import spring.server.dto.login.UserLoginRequest;
import spring.server.dto.login.UserSignupRequest;
import spring.server.repository.MemberRepository;
import spring.server.repository.email.EmailRepository;
import spring.server.result.error.exception.EmailAuthTokenNotFoundException;
import spring.server.result.error.exception.UserAlReadyExisted;
import spring.server.result.error.exception.UserNotFoundException;
import spring.server.service.email.EmailService;
import spring.server.util.JwtUtil;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthService {

    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder;

    public void login (UserLoginRequest userLoginRequest){

        //유저 업승ㅁ
        Member member = memberRepository.findByUsername(userLoginRequest.getUsername())
                .orElseThrow(UserNotFoundException::new);

        log.info("request={}, user pw={}", userLoginRequest.getPassword(), member.getPassword());

        //패스워드 틀림
        if(!encoder.matches(userLoginRequest.getPassword(), member.getPassword())){
            log.info("request={}, user pw={}", userLoginRequest.getPassword(), member.getPassword());
            throw new RuntimeException();
            //다른 Exception 던질거임
        }

    }

    @Transactional
    public void confirmEmail(EmailAuthRequest request){
        Email email = emailRepository.findValidAuthByEmail(request.getEmail(), request.getAuthToken(), LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFoundException::new);


        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        email.useToken();
        member.emailVerifiedSuccess();

    }

    @Transactional
    public UserSignUpResponse signup(UserSignupRequest request) throws MessagingException {
        //생성
//        if(userRepository.findByEmail(request.getEmail()).isPresent()){
//            //이메일 이미 존재 에러
//        }

        log.info("email 생성 시작");

        Email email = emailRepository.save(
                Email.builder()
                        .email(request.getEmail())
                        .authToken(UUID.randomUUID().toString())
                        .expired(false)
                        .build());

        log.info("email 생성 완료");

        log.info("user 생성 시작");

        Member member = memberRepository.save(
                Member.builder()
                        .email(request.getEmail())
                        //패스워드 encoder 넣어야됨
                        .password(encoder.encode(request.getPassword()))
                        .emailAuth(false)
                        .username(request.getUsername())
                        .roles("ROLE_USER")
                        .build());

        log.info("user  생성 완료");
        log.info("username={}", member.getUsername());

        HashMap<String, String> emailValues = new HashMap<>();
        emailValues.put("email", member.getEmail());
        emailService.send("이메일 인증", email.getEmail(), emailValues, email.getAuthToken());

        log.info("email send 완료");

        return UserSignUpResponse.builder()
                .email(member.getEmail())
                .authToken(email.getAuthToken())
                .build();


  }

}
