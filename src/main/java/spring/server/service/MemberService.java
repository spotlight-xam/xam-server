//package spring.server.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import spring.server.dto.member.MemberProfileResponse;
//import spring.server.entity.Member;
//import spring.server.repository.MemberRepository;
//import spring.server.result.error.exception.UserNotFoundException;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//
//    public MemberProfileResponse getMemberProfile(String username) {
//        Optional<Member> member = memberRepository.findByUsername(username);
//                .orElseThrow(UserNotFoundException::new)
//
//        return new MemberProfileResponse(member);
//
//    }
//}
