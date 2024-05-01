package fr.utc.sr03.chatapp.repos;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Chatroom findFirstByUsers(User user);

    List<Chatroom> findAllByUsers(User user);

}
