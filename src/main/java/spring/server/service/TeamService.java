package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.server.dto.team.*;
import spring.server.entity.Team;
import spring.server.entity.TeamUser;
import spring.server.entity.User;
import spring.server.repository.TeamRepository;
import spring.server.repository.UserRepository;
import spring.server.result.error.exception.TeamNotExistException;
import spring.server.result.error.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public CreateTeamResponse createTeam(CreateTeamRequest createTeamRequest) {

        Team team = new Team(createTeamRequest.getTeamName());

        teamRepository.save(team);

        final CreateTeamResponse createTeamResponse = new CreateTeamResponse();

        createTeamResponse.setTeamId(team.getId());

        return createTeamResponse;
    }

    public void addUser(AddUserRequest addUserRequest){

        Team team = teamRepository.findById(addUserRequest.getTeamId())
                .orElseThrow(TeamNotExistException::new);

//        addUserRequest.getUserList().stream()
//                .collect();
    }

    @Transactional
    public void removeTeam(Long teamId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);
        teamRepository.delete(team);
    }

    @Transactional
    public void removeUser(Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);



//        List<User> userList = teamUser.getUserList();
//        Iterator<User> iterator= userList.iterator();
//        while (iterator.hasNext()) {
//            User user = iterator.next();
//            if (user.getId() == userId) {
//                iterator.remove();
//            }
//        }
    }
    @Transactional
    public MyTeamResponse findTeamList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
//
//        TeamUser teamUser = user.getUserTeam();
//
//        List<Team> teamList = teamUser.getTeamList();

        MyTeamResponse myTeamResponse = new MyTeamResponse();
//        myTeamResponse.setMyTeamList(teamList);

        return myTeamResponse;
    }

    public TeamInfoResponse getTeamInfo(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotExistException::new);

//        List<User> userList = team.getTeamUser().getUserList();

        TeamInfoResponse teamInfoResponse = new TeamInfoResponse();
//        teamInfoResponse.setUserList(userList);




        return teamInfoResponse;
    }




}
