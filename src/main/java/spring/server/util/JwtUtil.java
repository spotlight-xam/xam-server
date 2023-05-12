package spring.server.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.server.repository.MemberRepository;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {
    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("username", String.class);
    }

    public static boolean isExpired(String token, String secretKey){

        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    //1분
    private static long access_token_expires =1000L * 60 * 50 * 60;

    public String createJwt(String username){

        Claims claims = Jwts.claims();
        claims.put("username", username);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expires))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

    }
}
