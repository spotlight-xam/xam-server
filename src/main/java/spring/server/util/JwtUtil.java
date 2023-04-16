package spring.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.server.entity.User;
import spring.server.repository.UserRepository;
import spring.server.result.error.exception.UserDoesNotExistException;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final UserRepository userRepository;

    public static String getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("username", String.class);
    }

    public static boolean isExpired(String token, String secretKey){

        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public Long getLoginUserId() {
        try {
            final String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
            return Long.valueOf(memberId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

//    public User getLoginUser() {
//        log.info("getLoginUser 실행");
//        try {
//            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            log.info("memberId={}", memberId);
//            return userRepository.findById(memberId).orElseThrow(UserDoesNotExistException::new);
//
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }

    //1분
    private static long access_token_expires =1000L * 60 + 10;

    public static String createJwt(String email, String secretKey){
        Claims claims = Jwts.claims();
        claims.put("email", email);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expires))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}
