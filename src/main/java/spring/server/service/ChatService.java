package spring.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.server.dto.chat.*;
import spring.server.dto.member.ChatRoomMemberInfo;
import spring.server.entity.Member;
import spring.server.entity.Room;
import spring.server.entity.RoomMember;
import spring.server.entity.chat.Chat;
import spring.server.entity.chat.ChatImage;
import spring.server.entity.chat.ChatText;
import spring.server.repository.chat.*;
import spring.server.repository.MemberRepository;
import spring.server.repository.room.RoomMemberRepository;
import spring.server.repository.room.RoomRepository;
import spring.server.result.error.exception.RoomNotExistException;
import spring.server.result.error.exception.UserNotFoundException;
import spring.server.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
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
    private final ChatImageRepository chatImageRepository;


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
        //TODO
        // roomMember를 저장하면서 room과 member에도 각각 업데이트쿼리를 날려야 함

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

    @Transactional
    public Page<ChatDto> getChatRoomMessages(Long roomId, Integer page) {

        page = (page == 0 ? 0 : page-1);
        Member loginMember = jwtUtil.getLoginMember();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);

        final Pageable pageable = PageRequest.of(page, 10);

        // TODO : chat_id 넣어서 chat_id 기준으로 페이징해서 가져오게하기
        Page<Chat> chatsByRoomId = roomRepository.findChatsByRoomId(room.getId(), pageable);

        List<ChatDto> chatDtos = convertChatToDto(chatsByRoomId.getContent());

        return new PageImpl<>(chatDtos, pageable, chatsByRoomId.getTotalElements());
    }

    private List<ChatDto> convertChatToDto(List<Chat> chats) {

        final List<Long> chatIds = getMessageIds(chats);

        final Map<Long, ChatImage> chatImageMap = chatImageRepository.findAllByIdIn(chatIds)
                .stream()
                .collect(Collectors.toMap(Chat::getId, ci -> ci));
        final Map<Long, ChatText> chatTextMap = chatTextRepository.findAllByIdIn(chatIds)
                .stream()
                .collect(Collectors.toMap(Chat::getId, ct -> ct));

        return chats.stream()
                .map(c -> {
                    switch (c.getDtype()) {
                        case "IMAGE":
                            return new ChatDto(chatImageMap.get(c.getId()));
                        case "TEXT":
                            return new ChatDto(chatTextMap.get(c.getId()));
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private static List<Long> getMessageIds(List<Chat> chats) {
        final List<Long> messageIds = chats.stream()
                .map(Chat::getId)
                .collect(Collectors.toList());
        return messageIds;
    }

    public Page<JoinRoomDto> getJoinRooms(Integer page) {
        final Member loginMember = jwtUtil.getLoginMember();
        page = (page == 0 ? 0 : page - 1);

        PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
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
