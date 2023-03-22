package spring.server;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring.server.entity.Email;
import spring.server.entity.User;

import javax.persistence.EntityManager;

@SpringBootApplication
public class TaekApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaekApplication.class, args);

		new User("wontaek88", "wontaek", "ghost75421");
	}

}
