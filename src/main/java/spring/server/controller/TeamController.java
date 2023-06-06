package spring.server.controller;

import groovy.util.logging.Slf4j;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.team.CreateTeamRequest;
import spring.server.dto.team.CreateTeamResponse;
import spring.server.dto.team.MyTeamResponse;
import spring.server.dto.team.TeamInfoResponse;
import spring.server.entity.Room;
import spring.server.service.TeamService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    //팀 생성
    @PostMapping("/create")
    public ResponseEntity<CreateTeamResponse> createTeam(CreateTeamRequest createTeamRequest) {
        return ResponseEntity.ok().body(teamService.createTeam(createTeamRequest));
    }

    //팀의 소속된 유저찾기
    @GetMapping("/{teamId}/users")
    public ResponseEntity<TeamInfoResponse> getTeamInfo(@PathVariable Long teamId) {
        return ResponseEntity.ok().body(teamService.getTeamInfo(teamId));
    }

    //팀의 유저 추가
    @PostMapping("/{teamId}/{memberId}")
    public void addTeamMember(@PathVariable Long teamId, @PathVariable Long memberId) {
        teamService.addTeamMember(teamId, memberId);
    }

    //유저의 소속된 팀 찾기
    @GetMapping("/myTeam")
    public ResponseEntity<Page<MyTeamResponse>> findTeamList(@RequestParam Integer page){
        return ResponseEntity.ok().body(teamService.findTeamList(page));
    }

    //팀의 소속 유저 제거
    @DeleteMapping("/{teamId}/remove/{userId}")
    public void removeUser(@PathVariable Long teamId, @PathVariable Long userId){
        teamService.removeMember(userId, teamId);
    }

    //팀 제거
    @DeleteMapping("remove/{teamId}")
    public void removeTeam(@PathVariable Long teamId){
        teamService.removeTeam(teamId);
    }


}