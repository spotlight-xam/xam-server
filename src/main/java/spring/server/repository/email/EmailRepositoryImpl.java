package spring.server.repository.email;

import com.querydsl.jpa.impl.JPAQueryFactory;
import spring.server.entity.Email;
import spring.server.entity.QEmail;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

public class EmailRepositoryImpl implements EmailCustomRepository{

    JPAQueryFactory jpaQueryFactory;

    public EmailRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Email> findValidAuthByEmail(String email, String authToken, LocalDateTime localDateTime) {
        Email emailAuth = jpaQueryFactory
                .selectFrom(QEmail.email1)
                .where(QEmail.email1.email.eq(email),
                        QEmail.email1.authToken.eq(authToken),
                        QEmail.email1.expireDate.goe(localDateTime),
                        QEmail.email1.expired.eq(false)
                ).fetchFirst();

        return Optional.ofNullable(emailAuth);
    }
}
