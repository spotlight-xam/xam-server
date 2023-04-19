package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.server.dto.chat.ChatDto;
import spring.server.dto.chat.CreateRoomRequest;
import spring.server.dto.chat.CreateRoomResponse;
import spring.server.dto.team.CreateTeamRequest;
import spring.server.entity.Chat;
import spring.server.service.ChatService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/enter")
    public void enterRoom(ChatDto chatDto) {
        chatService.enterRoom(chatDto);
    }

    @PostMapping("/message")
    public void sendMessage(ChatDto chatDto) {
        chatService.sendMessage(chatDto);
    }

    @PostMapping("/createroom")
    public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest createRoomRequest){
        return ResponseEntity.ok().body(chatService.createRoom(createRoomRequest));
    }

    @GetMapping
    public String chatGET(){

        log.info("chat Cotroller 실행");

        return "chat";
    }


}
