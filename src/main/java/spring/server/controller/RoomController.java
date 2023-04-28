package spring.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.server.dto.chat.RoomlistResponse;
import spring.server.repository.RoomRepository;
import spring.server.service.RoomService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

    private final RoomService roomService;

    private final RoomRepository roomRepository;


    @GetMapping("/rooms")
    public ResponseEntity<RoomlistResponse> findAllRoom() {
        log.info("findAllRoom Controller 실행");
        return ResponseEntity.ok().body(roomService.findAllRoom());
    }

//    @GetMapping(value = "/rooms")
//    public ModelAndView rooms(){
//
//        log.info("# All Chat Rooms");
//        ModelAndView mv = new ModelAndView("chat/rooms");
//
//        mv.addObject("list", roomRepository.findAll());
//
//        return mv;
//    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String roomName, RedirectAttributes rttr){
        log.info("# Create Chat Room , name: " + roomName);
        roomService.createRoom(roomName,rttr);
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(Long roomId, Model model){
        log.info("# get Chat Room, roomID : " + roomId);
        model.addAttribute("room", roomService.getRoom(roomId));
    }
}
