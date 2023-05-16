package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.server.entity.Feed;
import spring.server.entity.Image;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {



}
