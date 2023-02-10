package spring.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {

    @Value("${jwt.secret}")
    private static String secretKey;

    private static long access_token_expires =1000L * 60 * 60 * 24 * 7;

    public static String createJwt(String username){
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + access_token_expires))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
