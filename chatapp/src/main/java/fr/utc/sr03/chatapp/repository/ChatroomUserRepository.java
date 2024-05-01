package fr.utc.sr03.chatapp.repository;

import fr.utc.sr03.chatapp.entity.Chatroom;
import fr.utc.sr03.chatapp.entity.ChatroomUser;
import fr.utc.sr03.chatapp.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Long> {
    ChatroomUser findByUserAndChatroom(User user, Chatroom chatroom);
}
