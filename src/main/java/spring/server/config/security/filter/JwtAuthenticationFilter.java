package spring.server.config.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    private final AuthenticationManager authenticationManager;


    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //username, password 받아서
        try {
            final BufferedReader reader = request.getReader();

            String input = null;

            while ((input = reader.readLine()) != null) {
                log.info("input={}", input);
            }

            log.info("request.getInputStream={}", request.getInputStream().toString());
        } catch (IOException e) {

        }

        //정상인지 로그인 시도. AuthenticationManager로 로그인 시도
        //CustomUserDetails에 loadUserByUsername 호출

        //CustomUserDetails를 세션에 담고
        //세션에 안담으면 권한 관리가 안됨. 권한 관리를 안한다면? -> 세션에 굳이 담을 필요 없음음

       //jwt토큰을 만들어서 응답

        log.info("JwtAuthenticationFilter 실행");
        return super.attemptAuthentication(request, response);
    }
}
