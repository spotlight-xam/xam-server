package spring.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import spring.server.dto.chat.ChatDto;
import spring.server.dto.chat.CreateRoomRequest;
import spring.server.dto.chat.CreateRoomResponse;
import spring.server.entity.Member;
import spring.server.entity.Room;
import spring.server.repository.ChatRepository;
import spring.server.repository.MemberRepository;
import spring.server.repository.RoomRepository;
import spring.server.result.error.exception.RoomNotExistException;
import spring.server.util.JwtUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ObjectMapper mapper;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
//    private final SimpMessagingTemplate simpMessagingTemplate;

    public Room findRoomById(Long roomId){
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);

        return room;
    }

    public CreateRoomResponse createRoom(CreateRoomRequest createRoomRequest){

        final Member loginMember = jwtUtil.getLoginMember();

        final List<String> usernames = createRoomRequest.getUsernames();

        usernames.add(loginMember.getUsername());

        memberRepository.findAllByUsernameIn(usernames);

        final Room room;

        final boolean status;




    }

    //메세지 보내는 기능
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));;
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }

//    public void enterRoom(ChatDto message) {
//        message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
//        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }
//
//    public void sendMessage(ChatDto message) {
//        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }
}
