package spring.server.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;

@Service
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void send(String email, String authToken){

        log.info("emailService.send");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email+"@gmail.com");
        simpleMailMessage.setSubject("회원가입 이메일 인증입니다");
        simpleMailMessage.setText("http://localhost:8080/confirm-email?email="+email+"&authToken="+authToken);

        javaMailSender.send(simpleMailMessage);
    }
}
