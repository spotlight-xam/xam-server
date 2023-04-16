package spring.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.server.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
