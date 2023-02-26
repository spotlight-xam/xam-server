package spring.server.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.LinkOption;
import java.util.HashMap;

@Service
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;


    @Async
    public void send(String title, String email, HashMap<String, String> values, String authToken) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        messageHelper.setSubject("회원가입 이메일 인증");
        messageHelper.setTo(email+"@gmail.com");

        Context context = new Context();
        values.forEach((key, value) -> {
            context.setVariable(key, value);
        });

        String html = templateEngine.process("emailAuth", context);
        messageHelper.setText(html, true);

        log.info("emailService.send");
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(email+"@gmail.com");
//        simpleMailMessage.setSubject("회원가입 이메일 인증입니다");
//        simpleMailMessage.setText(
//                "http://localhost:8080/confirm-email?email="+email+"&authToken="+authToken
//        );

        javaMailSender.send(mimeMessage);
    }
}
