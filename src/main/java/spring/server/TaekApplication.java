package spring.server;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class TaekApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaekApplication.class, args);


	}

}
