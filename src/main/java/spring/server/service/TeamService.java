package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.server.dto.team.*;
import spring.server.entity.Team;
import spring.server.entity.Member;
import spring.server.entity.TeamMember;
import spring.server.repository.TeamMemberRepository;
import spring.server.repository.TeamRepository;
import spring.server.repository.MemberRepository;
import spring.server.result.error.exception.TeamNotExistException;
import spring.server.result.error.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest) {

        Team team = new Team("team1");

        log.info("=================teamName={}", team.getTeamName());

        teamRepository.save(team);

        final CreateTeamResponse createTeamResponse = new CreateTeamResponse();

        createTeamResponse.setTeamId(team.getId());

        return createTeamResponse;
    }

    @Transactional
    public void addTeamMember(Long teamId, Long memberId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);
        log.info("teamService.addTeamMember의 team={}", team.getTeamName());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);
        log.info("teamService.addTeamMember의 member={}", member.getUsername());

        TeamMember teamMember = new TeamMember(member, team);
        teamMemberRepository.save(teamMember);

        member.addTeamMember(teamMember);
        team.addTeamMember(teamMember);

        memberRepository.save(member);
        teamRepository.save(team);
    }

    @Transactional
    public void removeTeam(Long teamId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);

        teamRepository.delete(team);
    }

    @Transactional
    public void removeMember(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        List<TeamMember> teamMembers = team.getTeamMembers();
        List<TeamMember> memberTeams = member.getTeamMembers();

        //이거 서비스단에서 로직으로 푸는 방법 도 있지만 SQL 작성 해서 푸는 방법도 있을 듯 함.
        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getMember() == member) {
                teamMembers.remove(member);
                memberTeams.remove(teamMember);
            }
        }
    }

    @Transactional
    public MyTeamResponse findTeamList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        List<TeamMember> teamMembers = member.getTeamMembers();
        log.info("teamMebers={}", teamMembers);

        MyTeamResponse myTeamResponse = new MyTeamResponse();

        List<Team> myTeamList = myTeamResponse.getMyTeamList();

        for (TeamMember teamMember : teamMembers) {
            myTeamList.add(teamMember.getTeam());
        }

        return myTeamResponse;
    }

    public TeamInfoResponse getTeamInfo(Long teamId) {
        log.info("getTeamInfo 실행");
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);

//        List<User> userList = team.getTeamUser().getUserList();

        TeamInfoResponse teamInfoResponse = new TeamInfoResponse();

        List<TeamMember> teamMembers = team.getTeamMembers();
        log.info("teamMemberList={}", teamMembers);

        for (TeamMember teamMember : teamMembers) {
            teamInfoResponse.addMember(teamMember.getMember().getUsername());
        }

        teamInfoResponse.setTeamName(team.getTeamName());


        return teamInfoResponse;
    }




}