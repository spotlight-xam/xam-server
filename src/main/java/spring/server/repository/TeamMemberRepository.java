package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
