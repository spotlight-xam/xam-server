package spring.server.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import spring.server.config.security.filter.JwtAuthenticationFilter;
import spring.server.config.security.provider.CustomAuthenticationProvider;
import spring.server.service.UserAuthService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final UserAuthService userAuthService;
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().and()
                .apply(new MyCustomDsl())
                .and()
//                .addFilterBefore(new JwtFilter(userAuthService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .antMatchers("/signup", "/login", "/**").permitAll()
                .and().build();
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.headers().frameOptions().sameOrigin();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(new JwtAuthenticationFilter(authenticationManager));
        }
    }


}
