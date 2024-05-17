package fr.utc.sr03.chatapp.mapper;

import org.springframework.stereotype.Component;
import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserPublicDTO;
import fr.utc.sr03.chatapp.model.UserPublicWithStatsDTO;
import fr.utc.sr03.chatapp.model.UserPublicWithoutChatroomDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import groovy.lang.Singleton;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;

@Component
public final class UserMapper {

    // The field must be declared volatile so that double check lock would work
    // correctly.
    private static volatile ChatroomMapper chatroomMapper;

    public static ChatroomMapper getChatroomMapper() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate
        // instances as a result.
        //
        // It may seem that having the `result` variable here is completely
        // pointless. There is, however, a very important caveat when
        // implementing double-checked locking in Java, which is solved by
        // introducing this local variable.
        //
        // You can read more info DCL issues in Java here:
        // https://refactoring.guru/java-dcl-issue
        ChatroomMapper result = chatroomMapper;
        if (result != null) {
            return result;
        }
        synchronized (ChatroomMapper.class) {
            if (chatroomMapper == null) {
                chatroomMapper = new ChatroomMapper();
            }
            return chatroomMapper;
        }
    }

    public UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        // This is a workaround to avoid circular dependencies
        // See: https://stackoverflow.com/a/40702503
        // See:
        // https://docs.spring.io/spring-framework/docs/4.3.10.RELEASE/spring-framework-reference/htmlsingle/#beans-setter-injection
        ChatroomMapper chatroomMapper = getChatroomMapper();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setIsAdmin(user.getIsAdmin());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastConnection(user.getLastConnection());
        userDTO.setFailedConnectionAttempts(user.getFailedConnectionAttempts());
        userDTO.setIsLocked(user.getIsLocked());
        user.getChatrooms().forEach(
                chatroom -> userDTO.addChatroom(chatroomMapper.mapToDTO(chatroom, new ChatroomWithoutUserDTO())));
        return userDTO;
    }

    public UserPublicDTO mapToDTO(final User user, final UserPublicDTO userDTO) {
        ChatroomMapper chatroomMapper = new ChatroomMapper();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(user.getIsAdmin());
        user.getChatrooms().forEach(
                chatroom -> userDTO.addChatroom(chatroomMapper.mapToDTO(chatroom, new ChatroomWithoutUserDTO())));
        return userDTO;
    }

    public UserPublicWithStatsDTO mapToDTO(final User user, final UserPublicWithStatsDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(user.getIsAdmin());
        userDTO.setChatroomCount(Long.valueOf(user.getChatrooms().size()));
        return userDTO;
    }

    public UserWithoutChatroomDTO mapToDTO(final User user, final UserWithoutChatroomDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setIsAdmin(user.getIsAdmin());
        return userDTO;
    }

    public UserPublicWithoutChatroomDTO mapToDTO(final User user, final UserPublicWithoutChatroomDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(user.getIsAdmin());
        return userDTO;
    }

    public User mapToEntity(final UserDTO userDTO, final User user) {
        ChatroomMapper chatroomMapper = getChatroomMapper();

        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setIsAdmin(userDTO.getIsAdmin());
        userDTO.getChatrooms().forEach(
                chatroomDTO -> user.addChatroom(chatroomMapper.mapToEntity(chatroomDTO, new Chatroom())));
        return user;
    }

    public User mapToEntity(final UserWithoutChatroomDTO userDTO, final User user) {
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setIsAdmin(userDTO.getIsAdmin());
        return user;
    }

}
