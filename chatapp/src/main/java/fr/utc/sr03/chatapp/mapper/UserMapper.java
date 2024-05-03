package fr.utc.sr03.chatapp.mapper;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserPublicWithStatsDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import fr.utc.sr03.chatapp.repos.ChatroomUserRepository;

public class UserMapper {

    private final ChatroomUserRepository chatroomUserRepository;

    public UserMapper(final ChatroomUserRepository chatroomUserRepository) {
        this.chatroomUserRepository = chatroomUserRepository;
    }

    // private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
    //     userDTO.setId(user.getId());
    //     userDTO.setFirstName(user.getFirstName());
    //     userDTO.setLastName(user.getLastName());
    //     userDTO.setEmail(user.getEmail());
    //     userDTO.setPassword(user.getPassword());
    //     userDTO.setIsAdmin(user.getIsAdmin());
    //     userDTO.setChatrooms(chatroomRepository.countByUsers(user));
    //     return userDTO;
    // }    

    public UserPublicWithStatsDTO mapToDTO(final User user, final UserPublicWithStatsDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(user.getIsAdmin());
        userDTO.setChatroomCount(chatroomUserRepository.countByUser(user));
        return userDTO;
    }

    public User mapToEntity(final UserWithoutChatroomDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setIsAdmin(userDTO.getIsAdmin());
        return user;
    }

}
