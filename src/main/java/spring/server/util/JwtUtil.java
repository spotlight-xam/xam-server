package spring.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import spring.server.entity.User;
import spring.server.repository.UserRepository;
import spring.server.result.error.exception.UserDoesNotExistException;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private final UserRepository userRepository;

    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
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

    public User getLoginUser() {
        try {
            final Long memberId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
            return userRepository.findById(memberId).orElseThrow(UserDoesNotExistException::new);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    //일주일
    private static long access_token_expires =1000L * 60 * 60 * 24 * 7;

    public static String createJwt(String email, String secretKey){
        Claims claims = Jwts.claims();
        claims.put("email", email);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expires))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
