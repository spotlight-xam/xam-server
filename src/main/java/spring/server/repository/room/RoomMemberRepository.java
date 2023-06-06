package spring.server.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.server.entity.Member;
import spring.server.entity.RoomMember;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {


    @Query("select rm from RoomMember rm join fetch rm.member where rm.room.id =:roomId")
    public List<RoomMember> findAllByRoomId(@Param("roomId") Long roomId);

}
