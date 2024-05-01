package fr.utc.sr03.chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import fr.utc.sr03.chatapp.entity.User;
import fr.utc.sr03.chatapp.entity.Chatroom;
import fr.utc.sr03.chatapp.repository.UserRepository;
import fr.utc.sr03.chatapp.repository.ChatroomRepository;
import fr.utc.sr03.chatapp.entity.ChatroomUser;
import fr.utc.sr03.chatapp.repository.ChatroomUserRepository;

@SpringBootApplication
public class ChatappApplication {

	private static final Logger log = LoggerFactory.getLogger(ChatappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatappApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository, ChatroomRepository chatroomRepository, ChatroomUserRepository chatroomUserRepository) {
		return (args) -> {
			// save a few users
			// User user1 = new User("John", "Doe", "tes@test.f8", "password", true);
			// User user2 = new User("Jane", "Doe", "tes@test.fr772", "password", false);
			// userRepository.save(user1);
			// userRepository.save(user2);

			// // fetch all users
			// log.info("Users found with findAll():");

			// for (User user : userRepository.findAll()) {
			// 	log.info(user.toString());
			// }

			// save a few chatrooms
			// Chatroom chatroom1 = new Chatroom("Chatroom 1", "Description 1", new java.sql.Timestamp(System.currentTimeMillis()), new java.sql.Timestamp(System.currentTimeMillis()));
			// Chatroom chatroom2 = new Chatroom("Chatroom 2", "Description 2", new java.sql.Timestamp(System.currentTimeMillis()), new java.sql.Timestamp(System.currentTimeMillis()));
			// chatroomRepository.save(chatroom1);
			// chatroomRepository.save(chatroom2);

			// Chatroom chatroom2 = chatroomRepository.findById(2L).get();
			// chatroomRepository.delete(chatroom2);

			User user1 = userRepository.findById(1L).get();
			Chatroom chatroom1 = chatroomRepository.findById(1L).get();
			// user1.addChatroom(chatroom1);
			// userRepository.save(user1);
			ChatroomUser chatroomUser = chatroomUserRepository.findByUserAndChatroom(user1, chatroom1);
			user1.removeChatroom(chatroomUser.getChatroom());
			chatroom1.removeUser(chatroomUser.getUser());
			System.out.println(user1.getChatroomUsers());
			System.out.println(chatroom1.getChatroomUsers());
			// chatroomUserRepository.delete(chatroomUser);
		};
	}

}
