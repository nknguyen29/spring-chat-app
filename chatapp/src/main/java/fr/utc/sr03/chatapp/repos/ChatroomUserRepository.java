package fr.utc.sr03.chatapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.ChatroomUser;
import fr.utc.sr03.chatapp.domain.User;


public interface ChatroomUserRepository extends JpaRepository<ChatroomUser, Long> {

    ChatroomUser findByUserAndChatroom(User user, Chatroom chatroom);
    Long countByUser(User user);

}
