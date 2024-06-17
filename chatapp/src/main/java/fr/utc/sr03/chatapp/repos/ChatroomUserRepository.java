package fr.utc.sr03.chatapp.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.ChatroomUser;
import fr.utc.sr03.chatapp.domain.User;


public interface ChatroomUserRepository extends JpaRepository<ChatroomUser, Long> {

    Optional<ChatroomUser> findByUserAndChatroom(User user, Chatroom chatroom);
    Optional<ChatroomUser> findByUserIdAndChatroomId(Long userId, Long chatroomId);
    Long countByUser(User user);

    void deleteByChatroomAndUser(Chatroom chatroom, User user);
    void deleteByChatroomIdAndUserId(Long chatroomId, Long userId);

}
