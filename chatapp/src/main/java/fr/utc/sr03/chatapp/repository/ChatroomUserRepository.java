package fr.utc.sr03.chatapp.repository;

import fr.utc.sr03.chatapp.entity.ChatroomUser;
import org.springframework.data.repository.CrudRepository;

public interface ChatroomUserRepository extends CrudRepository<ChatroomUser, Long> {
}
