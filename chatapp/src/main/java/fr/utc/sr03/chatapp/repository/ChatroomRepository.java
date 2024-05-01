package fr.utc.sr03.chatapp.repository;

import org.springframework.data.repository.CrudRepository;

import fr.utc.sr03.chatapp.domain.Chatroom;

public interface ChatroomRepository extends CrudRepository<Chatroom, Long> {
}
