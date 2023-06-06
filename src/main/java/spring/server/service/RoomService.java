package spring.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.server.dto.room.RoomDto;
import spring.server.entity.Room;
import spring.server.repository.MemberRepository;
import spring.server.repository.room.RoomRepository;
import spring.server.result.error.exception.RoomNotExistException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createRoom(String roomName, RedirectAttributes rttr) {

        Room room = new Room(roomName);
        rttr.addFlashAttribute("roomName",roomRepository.save(room));
    }

    public Room getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);

        return room;
    }

    @Transactional
    public Page<RoomDto> getRooms(Long teamId, int page) {

        page = (page == 0 ? 0 : page - 1);
        final Pageable pageable = PageRequest.of(page, 10);

        Page<Room> rooms = roomRepository.findByTeamId(teamId, pageable);

        List<RoomDto> roomDtos = convertRoomDto(rooms.getContent());

        return new PageImpl<>(roomDtos, pageable, rooms.getTotalElements());

    }

    @Transactional
    public void updateName(Long roomId, String name) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomNotExistException::new);

        room.updateName(name);
    }

    private List<RoomDto> convertRoomDto(List<Room> rooms) {
        return rooms.stream()
                .map(room -> new RoomDto(room))
                .collect(Collectors.toList());
    }

}
