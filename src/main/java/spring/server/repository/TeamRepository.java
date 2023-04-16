package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
