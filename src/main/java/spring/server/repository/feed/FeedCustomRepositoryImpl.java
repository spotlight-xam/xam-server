package spring.server.repository.feed;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import spring.server.dto.feed.FeedResponse;

import spring.server.dto.image.ImageResponse;
import spring.server.entity.QFeed;
import spring.server.entity.QImage;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FeedCustomRepositoryImpl implements FeedCustomRepository{

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public FeedCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


}
