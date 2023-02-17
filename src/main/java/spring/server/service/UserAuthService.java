package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.server.dto.login.EmailAuthRequest;
import spring.server.dto.login.UserSignUpResponse;
import spring.server.entity.Email;
import spring.server.entity.User;
import spring.server.dto.login.UserLoginRequest;
import spring.server.dto.login.UserSignupRequest;
import spring.server.repository.UserRepository;
import spring.server.repository.email.EmailCustomRepository;
import spring.server.repository.email.EmailRepository;
import spring.server.repository.email.EmailRepositoryImpl;
import spring.server.service.email.EmailService;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String secretKey;

    public String login(UserLoginRequest userLoginRequest){

        //유저 업승ㅁ
        User user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(RuntimeException::new);
//                .orElseThrow(EmailIsAlreadyExisted::new)

        log.info("request={}, user pw={}", userLoginRequest.getPassword(), user.getPassword());

        //패스워드 틀림
        if(!encoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            log.info("request={}, user pw={}", userLoginRequest.getPassword(), user.getPassword());
            throw new RuntimeException();
            //다른 Exception 던질거임
        }

        return JwtUtil.createJwt(userLoginRequest.getEmail(), secretKey);


    }

    @Transactional
    public void confirmEmail(EmailAuthRequest request){
        Email email = emailRepository.findValidAuthByEmail(request.getEmail(), request.getAuthToken(), LocalDateTime.now())
                    .orElseThrow(RuntimeException::new);
//                .orElseThrow(EmailAuthTokenNotFoundException::new);


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(RuntimeException::new);
//                .orElseThrow(UserNotFoundException::new);

        email.useToken();
        user.emailVerifiedSuccess();

    }

    @Transactional
    public UserSignUpResponse signup(UserSignupRequest request) {
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

        User user = userRepository.save(
                User.builder()
                        .email(request.getEmail())
                        //패스워드 encoder 넣어야됨
                        .password(encoder.encode(request.getPassword()))
                        .emailAuth(false)
                        .build());

        log.info("user  생성 완료");

        emailService.send(email.getEmail(), email.getAuthToken());

        log.info("email send 완료");

        return UserSignUpResponse.builder()
                .email(user.getEmail())
                .authToken(email.getAuthToken())
                .build();

  }

}
