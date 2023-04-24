package spring.server.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
//import spring.server.config.security.filter.JwtFilter;
import spring.server.config.security.provider.CustomAuthenticationProvider;
import spring.server.service.UserAuthService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserAuthService userAuthService;
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Value("jwt.secret")
    private String secretKey;

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        //권한을 유저로 넣어줌
        authenticationManagerBuilder.userDetailsService(userDetailsService);

        //Provider 추가
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);


    }

    //정적 리소스를 보안필터 거치지 않고 넘김
    public void configure(WebSecurity wb) {
        wb.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .addFilterBefore(new JwtFilter(userAuthService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .antMatchers("/signup", "/login", "/**").permitAll()
                .and().build();
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.headers().frameOptions().sameOrigin();
    }


}
