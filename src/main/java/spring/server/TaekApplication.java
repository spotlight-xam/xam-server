package spring.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.server.entity.Member;

@SpringBootApplication
public class TaekApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaekApplication.class, args);

		new Member("wontaek88", "wontaek", "ghost75421");
	}

}
