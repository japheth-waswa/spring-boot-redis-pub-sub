package dev.japhethwaswa.demoapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static Dotenv dotenv;
	public static void main(String[] args) {
		dotenv = Dotenv.configure().load();
		SpringApplication.run(Application.class, args);
	}

}
