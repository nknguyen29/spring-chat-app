package fr.utc.sr03.chatapp.mapper;

import org.springframework.stereotype.Component;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.model.ChatroomPublicDTO;
import fr.utc.sr03.chatapp.model.ChatroomPublicWithoutUserDTO;
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
        chatroomDTO.setCreatedAt(chatroom.getCreatedAt());
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserWithoutChatroomDTO()));
        chatroomDTO.setUpdatedAt(chatroom.getUpdatedAt());
        chatroomDTO.setUpdatedBy(userMapper.mapToDTO(chatroom.getUpdatedBy(), new UserWithoutChatroomDTO()));
        chatroomDTO.setUsers(chatroom.getUsers().stream()
                .map(user -> userMapper.mapToDTO(user, new UserWithoutChatroomDTO()))
                .toList());
        return chatroomDTO;
    }

    public ChatroomPublicDTO mapToDTO(final Chatroom chatroom, final ChatroomPublicDTO chatroomDTO) {
        final UserMapper userMapper = getChatroomMapper();

        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroomDTO.setCreatedAt(chatroom.getCreatedAt());
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserPublicWithoutChatroomDTO()));
        chatroomDTO.setUpdatedAt(chatroom.getUpdatedAt());
        chatroomDTO.setUpdatedBy(userMapper.mapToDTO(chatroom.getUpdatedBy(), new UserPublicWithoutChatroomDTO()));
        chatroomDTO.setUsers(chatroom.getUsers().stream()
                .map(user -> userMapper.mapToDTO(user, new UserPublicWithoutChatroomDTO()))
                .toList());
        return chatroomDTO;
    }

    public ChatroomWithoutUserDTO mapToDTO(final Chatroom chatroom, final ChatroomWithoutUserDTO chatroomDTO) {
        final UserMapper userMapper = getChatroomMapper();

        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroomDTO.setCreatedAt(chatroom.getCreatedAt());
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserWithoutChatroomDTO()));
        chatroomDTO.setUpdatedAt(chatroom.getUpdatedAt());
        chatroomDTO.setUpdatedBy(userMapper.mapToDTO(chatroom.getUpdatedBy(), new UserWithoutChatroomDTO()));
        return chatroomDTO;
    }

    public ChatroomPublicWithoutUserDTO mapToDTO(final Chatroom chatroom, final ChatroomPublicWithoutUserDTO chatroomDTO) {
        final UserMapper userMapper = getChatroomMapper();

        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroomDTO.setCreatedAt(chatroom.getCreatedAt());
        chatroomDTO.setCreatedBy(userMapper.mapToDTO(chatroom.getCreatedBy(), new UserPublicWithoutChatroomDTO()));
        chatroomDTO.setUpdatedAt(chatroom.getUpdatedAt());
        chatroomDTO.setUpdatedBy(userMapper.mapToDTO(chatroom.getUpdatedBy(), new UserPublicWithoutChatroomDTO()));
        return chatroomDTO;
    }

    // private Chatroom mapToEntity(final ChatroomDTO chatroomDTO, final Chatroom chatroom) {
    //     chatroom.setTitle(chatroomDTO.getTitle());
    //     chatroom.setDescription(chatroomDTO.getDescription());
    //     chatroom.setStartDate(chatroomDTO.getStartDate());
    //     chatroom.setValidityDuration(chatroomDTO.getValidityDuration());
    //     final List<User> users = userRepository.findAllById(
    //             chatroomDTO.getUsers() == null ? Collections.emptyList() : chatroomDTO.getUsers());
    //     if (users.size() != (chatroomDTO.getUsers() == null ? 0 : chatroomDTO.getUsers().size())) {
    //         throw new NotFoundException("one of users not found");
    //     }
    //     chatroom.setUsers(new HashSet<>(users));
    //     return chatroom;
    // }

}
