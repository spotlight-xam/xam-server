package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.server.entity.Member;
import spring.server.repository.MemberRepository;
import spring.server.result.error.exception.UserNotFoundException;

import java.util.Collections;


@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername 실행");
        return memberRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(UserNotFoundException::new);

    }

    private UserDetails createUserDetails(Member member) {
        // 나중에는 "USER" -> member.getRole().toString() 같은 방식으로 리팩토링 필요
        final GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority));
    }
}
