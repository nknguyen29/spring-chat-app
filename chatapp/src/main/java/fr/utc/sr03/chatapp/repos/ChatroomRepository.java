package fr.utc.sr03.chatapp.repos;

import fr.utc.sr03.chatapp.domain.Chatroom;
import org.springframework.data.repository.CrudRepository;

public interface ChatroomRepository extends CrudRepository<Chatroom, Long> {
}
