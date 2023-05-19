package spring.server.repository.image;


import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.server.dto.image.ImageResponse;
import spring.server.dto.image.QImageResponse;
import spring.server.entity.Feed;
import spring.server.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
