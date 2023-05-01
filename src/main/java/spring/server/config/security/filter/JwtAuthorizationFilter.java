package spring.server.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import spring.server.config.security.PrincipalDetails;
import spring.server.entity.Member;
import spring.server.repository.MemberRepository;
import spring.server.result.error.exception.UserNotFoundException;
import spring.server.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


/**
 * 권한이나 인증이 필요한 특정 주소를 요청하면 이 필터를 탐
 * 권한이나 인증이 필요한 주소가 아니라면 이 필터를 거치지 않음
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String secretkey = this.secretKey;

        log.info("JwtAuthorizationFilter실행");

        String authorization = request.getHeader("Authorization");
        log.info("AAAAAAAAAauthorization={}", authorization);

        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("authorization이 잘못되었습니다");
            chain.doFilter(request, response);
        }

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");


        /**
         * HMAC256이 HS256과 대칭인듯 함
         * secret을 위에서 value값으로 가져와 선언해준 secretKey를 써야 하지만
         * (secretkey가 노출되면 안되기 때문)
         * secretKey 값에 계속 null 값이 들어감. 이유를 모르겠음
         * 임시 방편으로 secretKey 값을 보이게 넣어버림
         */
        String username = JWT.require(Algorithm.HMAC256("wontaekjwtsecretkey")).build().verify(jwtToken)
                .getClaim("username").asString();

        //서명이 정상적으로 됨
        if (username != null) {
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(UserNotFoundException::new);

            PrincipalDetails principalDetails = new PrincipalDetails(member);


            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                            null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                            principalDetails.getAuthorities()
                    );

            //시큐리티 세션에 authentication 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
