package fr.utc.sr03.chatapp.repos;

import fr.utc.sr03.chatapp.domain.ChatroomUser;
import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Long> {

    ChatroomUser findByUserAndChatroom(User user, Chatroom chatroom);

}
