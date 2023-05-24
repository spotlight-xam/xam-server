package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.server.dto.chat.ChatDto;
import spring.server.dto.chat.CreateRoomRequest;
import spring.server.dto.chat.CreateRoomResponse;
import spring.server.dto.chat.MessageRequest;
import spring.server.service.ChatService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping(value = "enter")
//    public void enter(ChatDto message){
//        message.setMessage(message.getSender() + "님이 채팅방에 참여하였습니다.");
//        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }
//
//    @MessageMapping(value = "/message")
//    public void message(ChatDto message){
//        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }

    @PostMapping("/createroom")
    public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest createRoomRequest){
        return ResponseEntity.ok().body(chatService.createRoom(createRoomRequest));
    }

    @MessageMapping("/messages")
    public void sendMessage(MessageRequest messageRequest) {
        chatService.sendMessage(messageRequest);
    }

    @GetMapping("/chat/rooms/{roomId}/messages")
    public ResponseEntity<Page<ChatDto>> getChatRoomMessages(@PathVariable Long roomId, @RequestParam int page) {
        return ResponseEntity.ok().body(chatService.getChatRoomMessages(roomId, page));
    }

    @GetMapping
    public String chatGET(){

        log.info("chat Cotroller 실행");

        return "chat";
    }


}
