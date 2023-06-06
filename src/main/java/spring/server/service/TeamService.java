package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.server.dto.team.*;
import spring.server.entity.Team;
import spring.server.entity.Member;
import spring.server.entity.TeamMember;
import spring.server.repository.TeamMemberRepository;
import spring.server.repository.TeamRepository;
import spring.server.repository.MemberRepository;
import spring.server.repository.room.RoomRepository;
import spring.server.result.error.exception.TeamNotExistException;
import spring.server.result.error.exception.UserNotFoundException;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final JwtUtil jwtUtil;
    private final RoomRepository roomRepository;

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest) {

        final Team team = Team.builder()
                .teamName(createTeamRequest.getTeamName())
                .profileImage(createTeamRequest.getProfileImage())
                .build();

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
    public Page<MyTeamResponse> findTeamList(Integer page) {

        final Member loginMember = jwtUtil.getLoginMember();

        page = (page == 0 ? 0 : page-1);
        final Pageable pageable = PageRequest.of(page, 10);

        List<TeamMember> teamMembers = loginMember.getTeamMembers();
        List<MyTeamResponse> myTeamResponseList = new ArrayList<>();

        for (TeamMember teamMember : teamMembers) {
            MyTeamResponse myTeamResponse = new MyTeamResponse();
            myTeamResponse.setTeamId(teamMember.getTeam().getId());
            myTeamResponse.setProfileImage(teamMember.getTeam().getProfileImage());
            myTeamResponseList.add(myTeamResponse);
        }

        return new PageImpl<>(myTeamResponseList, pageable, myTeamResponseList.size());
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