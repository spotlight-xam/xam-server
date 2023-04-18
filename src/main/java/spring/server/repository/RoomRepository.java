package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
