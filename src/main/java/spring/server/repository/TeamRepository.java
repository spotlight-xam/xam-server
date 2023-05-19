package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.server.entity.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
