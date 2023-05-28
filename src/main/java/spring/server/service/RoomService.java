package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.server.dto.chat.RoomlistResponse;
import spring.server.entity.Room;
import spring.server.repository.MemberRepository;
import spring.server.repository.chat.RoomRepository;
import spring.server.result.error.exception.RoomNotExistException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public void createRoom(String roomName, RedirectAttributes rttr) {

        Room room = new Room(roomName);

        rttr.addFlashAttribute("roomName",roomRepository.save(room));
    }

    public Room getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);

        return room;
    }

    public RoomlistResponse findAllRoom() {
        List<Room> allRoom = roomRepository.findAll();
        RoomlistResponse roomlistResponse = new RoomlistResponse();
        roomlistResponse.setRoomList(allRoom);

        return roomlistResponse;
    }
}
