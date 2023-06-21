package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.server.dto.chat.CreateRoomRequest;
import spring.server.dto.chat.CreateRoomResponse;
import spring.server.dto.room.RoomDto;
import spring.server.repository.room.RoomRepository;
import spring.server.service.RoomService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final RoomRepository roomRepository;

    //채팅방 만들기
    @PostMapping("/createroom")
    public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest createRoomRequest){
        return ResponseEntity.ok().body(roomService.createRoom(createRoomRequest));
    }

    //팀에 속해있는 ROOM 조회
    @GetMapping("/{teamId}/rooms")
    public ResponseEntity<Page<RoomDto>> getRooms(@PathVariable Long teamId, @RequestParam int page) {
        return ResponseEntity.ok().body(roomService.getRooms(teamId, page));
    }

    //채팅방 이름 변경
    @PostMapping("/{roomId}/rename")
    public void updateName(@PathVariable Long roomId, @RequestBody String name) {
        roomService.updateName(roomId, name);
    }

    //채팅방 삭제
    @DeleteMapping("{roomId}/delete")
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
    }
}
