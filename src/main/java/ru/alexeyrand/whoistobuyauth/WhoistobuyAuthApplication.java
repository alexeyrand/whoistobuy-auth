package ru.alexeyrand.whoistobuyauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
@EntityScan("ru.alexeyrand.*")
public class WhoistobuyAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhoistobuyAuthApplication.class, args);
	}

}
