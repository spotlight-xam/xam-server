package spring.server.repository.image;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.query.Param;
import spring.server.dto.image.ImageResponse;
import spring.server.dto.image.QImageResponse;
import spring.server.entity.Image;
import spring.server.entity.QFeed;
import spring.server.entity.QImage;

import javax.persistence.EntityManager;
import java.util.List;

public class ImageRepositoryImpl implements ImageCustomRepository{

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public ImageRepositoryImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public List<ImageResponse> findImageByFeedId(Long feedId){

        QFeed feed = QFeed.feed;
        QImage image = QImage.image;

        return jpaQueryFactory
                .select(new QImageResponse(
                    image.id, image.imageUrl
                ))
                .from(feed)
                .join(feed.images, image)
                .where(feed.id.eq(feedId))
                .fetch();
    }




}
