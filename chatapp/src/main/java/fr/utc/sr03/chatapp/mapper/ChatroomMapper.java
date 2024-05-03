package fr.utc.sr03.chatapp.mapper;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.model.ChatroomPublicDTO;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.model.ChatroomWithStatsDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import fr.utc.sr03.chatapp.model.UserPublicWithoutChatroomDTO;

public class ChatroomMapper {

    private final UserMapper userMapper;

    public ChatroomMapper(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ChatroomDTO mapToDTO(final Chatroom chatroom, final ChatroomDTO chatroomDTO) {
        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroom.getUsers().forEach(
                user -> chatroomDTO.addUser(userMapper.mapToDTO(user, new UserWithoutChatroomDTO())));
        return chatroomDTO;
    }

    public ChatroomPublicDTO mapToDTO(final Chatroom chatroom, final ChatroomPublicDTO chatroomDTO) {
        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroom.getUsers().forEach(
                user -> chatroomDTO.addUser(userMapper.mapToDTO(user, new UserPublicWithoutChatroomDTO())));
        return chatroomDTO;
    }

    public ChatroomWithoutUserDTO mapToDTO(final Chatroom chatroom, final ChatroomWithoutUserDTO chatroomDTO) {
        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        return chatroomDTO;
    }

    public ChatroomWithStatsDTO mapToDTO(final Chatroom chatroom, final ChatroomWithStatsDTO chatroomDTO) {
        chatroomDTO.setId(chatroom.getId());
        chatroomDTO.setTitle(chatroom.getTitle());
        chatroomDTO.setDescription(chatroom.getDescription());
        chatroomDTO.setStartDate(chatroom.getStartDate());
        chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
        chatroomDTO.setUserCount(Long.valueOf(chatroom.getUsers().size()));
        return chatroomDTO;
    }

    public Chatroom mapToEntity(final ChatroomDTO chatroomDTO, final Chatroom chatroom) {
        chatroom.setTitle(chatroomDTO.getTitle());
        chatroom.setDescription(chatroomDTO.getDescription());
        chatroom.setStartDate(chatroomDTO.getStartDate());
        chatroom.setValidityDuration(chatroomDTO.getValidityDuration());
        chatroomDTO.getUsers().forEach(
                userDTO -> chatroom.addUser(userMapper.mapToEntity(userDTO, new User())));
        return chatroom;
    }

    public Chatroom mapToEntity(final ChatroomWithoutUserDTO chatroomDTO, final Chatroom chatroom) {
        chatroom.setTitle(chatroomDTO.getTitle());
        chatroom.setDescription(chatroomDTO.getDescription());
        chatroom.setStartDate(chatroomDTO.getStartDate());
        chatroom.setValidityDuration(chatroomDTO.getValidityDuration());
        return chatroom;
    }

    public Chatroom mapToEntity(final ChatroomWithStatsDTO chatroomDTO, final Chatroom chatroom) {
        chatroom.setTitle(chatroomDTO.getTitle());
        chatroom.setDescription(chatroomDTO.getDescription());
        chatroom.setStartDate(chatroomDTO.getStartDate());
        chatroom.setValidityDuration(chatroomDTO.getValidityDuration());
        return chatroom;
    }

}
