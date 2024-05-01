package fr.utc.sr03.chatapp.repository;

import fr.utc.sr03.chatapp.entity.Chatroom;
import org.springframework.data.repository.CrudRepository;

public interface ChatroomRepository extends CrudRepository<Chatroom, Long> {
}
