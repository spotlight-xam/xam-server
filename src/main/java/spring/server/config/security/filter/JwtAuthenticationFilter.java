package spring.server.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.server.config.security.PrincipalDetails;
import spring.server.dto.login.UserLoginRequest;
import spring.server.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.info("attemptAuthentication 실행");

        UserLoginRequest user = null;

        //username, password 받아서
        try {
            ObjectMapper om = new ObjectMapper();
            user = om.readValue(request.getInputStream(), UserLoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //CustomUserDetailsService함수의 loadUserByUsername 실행
        log.info("authToken에 담기 전 username={}", user.getUsername());
        log.info("authToken에 담기 전 password={}", user.getPassword());
        //db에 있는 username과 password 가 일치
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);


        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        log.info("11111111username={}", principal.getUsername());
        log.info("1111111111password={}", principal.getPassword());



        log.info("JwtAuthenticationFilter 실행");
        //authentication객체가 세션에 저장
        return authentication;
    }

    //attemptAuthentication에서 인증이 정상적으로 완료되면 그 후에 실행
    //jwt토큰을 만들어서 jwt토큰 넘겨주면 됨

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication 실행");

        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        log.info("principal={}", principal.getUsername());

        final String jwtToken = jwtUtil.createJwt(principal.getUsername());
        log.info("jwtToken={}", jwtToken);

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
