package fr.utc.sr03.chatapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.utc.sr03.chatapp.domain.Chatroom;


public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
