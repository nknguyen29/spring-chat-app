package fr.utc.sr03.chatapp.mapper;

import org.springframework.stereotype.Component;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.model.ChatroomPublicDTO;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.model.UserPublicWithoutChatroomDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;


@Component
public class ChatroomMapper {

    private static volatile UserMapper userMapper;

    public static UserMapper getChatroomMapper() {
        final UserMapper result = userMapper;
        if (result != null) {
            return result;
        }
        synchronized (UserMapper.class) {
            if (userMapper == null) {
                userMapper = new UserMapper();
            }
            return userMapper;
        }
    }

    public ChatroomDTO mapToDTO(final Chatroom chatroom, final ChatroomDTO chatroomDTO) {
        final UserMapper userMapper = getChatroomMapper();

        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroom.getUsers().forEach(
                user -> chatroomDTO.addUser(userMapper.mapToDTO(user, new UserWithoutChatroomDTO())));
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserWithoutChatroomDTO()));
        return chatroomDTO;
    }

    public ChatroomPublicDTO mapToDTO(final Chatroom chatroom, final ChatroomPublicDTO chatroomDTO) {
        final UserMapper userMapper = getChatroomMapper();

        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroom.getUsers().forEach(
                user -> chatroomDTO.addUser(userMapper.mapToDTO(user, new UserPublicWithoutChatroomDTO())));
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserWithoutChatroomDTO()));
        return chatroomDTO;
    }

    public ChatroomWithoutUserDTO mapToDTO(final Chatroom chatroom, final ChatroomWithoutUserDTO chatroomDTO) {
        final UserMapper userMapper = getChatroomMapper();

        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserWithoutChatroomDTO()));
        return chatroomDTO;
    }

}
