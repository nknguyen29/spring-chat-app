package fr.utc.sr03.chatapp.repos;

import fr.utc.sr03.chatapp.domain.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
