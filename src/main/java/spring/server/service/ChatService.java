package spring.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.server.dto.chat.MessageRequest;
import spring.server.dto.chat.CreateRoomRequest;
import spring.server.dto.chat.CreateRoomResponse;
import spring.server.dto.member.ChatRoomMemberInfo;
import spring.server.entity.Member;
import spring.server.entity.Room;
import spring.server.entity.RoomMember;
import spring.server.entity.chat.ChatText;
import spring.server.repository.chat.ChatRepository;
import spring.server.repository.MemberRepository;
import spring.server.repository.RoomRepository;
import spring.server.repository.chat.ChatTextRepository;
import spring.server.repository.chat.RoomMemberRepository;
import spring.server.result.error.exception.RoomNotExistException;
import spring.server.result.error.exception.UserNotFoundException;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ObjectMapper mapper;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final ChatTextRepository chatTextRepository;

    private final JwtUtil jwtUtil;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public Room findRoomById(Long roomId){
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);

        return room;
    }

    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest createRoomRequest){

        final Member loginMember = jwtUtil.getLoginMember();
        final List<String> usernames = createRoomRequest.getUsernames();

        usernames.add(loginMember.getUsername());

        List<Member> members = memberRepository.findAllByUsernameIn(usernames);

        final Room room = new Room(createRoomRequest.getRoomName());
        roomRepository.save(room);
        //roomMember를 저장하면서 room과 member에도 각각 업데이트쿼리를 날려야 함

        List<ChatRoomMemberInfo> memberInfos = members.stream()
                .map(ChatRoomMemberInfo::new)
                .collect(Collectors.toList());

        return new CreateRoomResponse(room.getId(), new ChatRoomMemberInfo(loginMember), memberInfos);

    }

    //메세지 보내는 기능
    @Transactional
    public void sendMessage(MessageRequest messageRequest){
        Member member = memberRepository.findById(messageRequest.getSenderId())
                .orElseThrow(UserNotFoundException::new);

        Room room = roomRepository.findById(messageRequest.getRoomId())
                .orElseThrow(RoomNotExistException::new);

        List<RoomMember> roomMembers = roomMemberRepository.findAllByRoomId(room.getId());

        ChatText chatText = new ChatText(room, member, messageRequest.getMessage());
        chatText.setDtype();
        chatRepository.save(chatText);

        roomMembers.forEach(r -> simpMessagingTemplate.convertAndSend("/sub/" + r.getMember().getUsername()));
    }

    public Object getChatRoomMessages(Long roomId, int page) {
        Member loginMember = jwtUtil.getLoginMember();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);



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
