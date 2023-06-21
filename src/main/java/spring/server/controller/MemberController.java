package spring.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.server.dto.member.MemberProfileResponse;
import spring.server.service.MemberService;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //멤버의 프로필 조회
    @GetMapping("/{username}")
    public ResponseEntity<MemberProfileResponse> getMemberProfile(@PathVariable String username) {
        return ResponseEntity.ok().body(memberService.getMemberProfile(username));
    }

}
