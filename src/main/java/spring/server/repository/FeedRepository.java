package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
