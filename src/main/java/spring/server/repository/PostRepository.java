package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Feed;

public interface PostRepository extends JpaRepository<Feed, Long> {
}
