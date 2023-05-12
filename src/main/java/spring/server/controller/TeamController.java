package spring.server.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.team.CreateTeamRequest;
import spring.server.dto.team.CreateTeamResponse;
import spring.server.dto.team.MyTeamResponse;
import spring.server.dto.team.TeamInfoResponse;
import spring.server.service.TeamService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<CreateTeamResponse> createTeam(CreateTeamRequest createTeamRequest) {
        return ResponseEntity.ok().body(teamService.createTeam(createTeamRequest));
    }

    @GetMapping("/{teamId}/users")
    public ResponseEntity<TeamInfoResponse> getTeamInfo(@PathVariable Long teamId) {
        return ResponseEntity.ok().body(teamService.getTeamInfo(teamId));
    }

    @PostMapping("/{teamId}/{memberId}")
    public void addTeamMember(@PathVariable Long teamId, @PathVariable Long memberId) {
        teamService.addTeamMember(teamId, memberId);
    }

    @GetMapping("/{memberId}/myTeam")
    public ResponseEntity<MyTeamResponse> findTeamList(@PathVariable Long memberId){
        return ResponseEntity.ok().body(teamService.findTeamList(memberId));
    }

    @DeleteMapping("/remove/{teamId}/{userId}")
    public void removeUser(@PathVariable Long teamId, @PathVariable Long userId){
        teamService.removeMember(userId, teamId);
    }

    @DeleteMapping("remove/{teamId}")
    public void removeTeam(@PathVariable Long teamId){
        teamService.removeTeam(teamId);
    }

}