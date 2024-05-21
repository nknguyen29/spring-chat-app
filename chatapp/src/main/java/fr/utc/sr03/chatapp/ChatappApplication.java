package fr.utc.sr03.chatapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ChatappApplication {

	private static final Logger log = LoggerFactory.getLogger(ChatappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatappApplication.class, args);
	}

}
