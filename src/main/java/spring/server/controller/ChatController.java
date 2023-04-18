package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.server.dto.chat.CreateRoomRequest;
import spring.server.dto.chat.CreateRoomResponse;
import spring.server.dto.team.CreateTeamRequest;
import spring.server.service.ChatService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest createRoomRequest){
        return ResponseEntity.ok().body(chatService.createRoom(createRoomRequest));
    }

    @GetMapping("/chat")
    public String chatGET(){

        return "chat";
    }


}
