package fr.utc.sr03.chatapp.repository;

import org.springframework.data.repository.CrudRepository;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.ChatroomUser;
import fr.utc.sr03.chatapp.domain.User;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Long> {
    ChatroomUser findByUserAndChatroom(User user, Chatroom chatroom);
}
