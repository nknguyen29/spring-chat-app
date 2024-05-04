package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.mapper.UserMapper;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserPublicDTO;
import fr.utc.sr03.chatapp.model.UserPublicWithStatsDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import fr.utc.sr03.chatapp.repos.ChatroomUserRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import fr.utc.sr03.chatapp.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ChatroomUserRepository chatroomUserRepository;

    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository,
            final ChatroomUserRepository chatroomUserRepository,
            final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;

        this.userMapper = userMapper;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> userMapper.mapToDTO(user, new UserDTO()))
                .toList();
    }

    public List<UserPublicWithStatsDTO> findAllWithStats() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> userMapper.mapToDTO(user, new UserPublicWithStatsDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UserPublicWithStatsDTO getPublicWithStats(final Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.mapToDTO(user, new UserPublicWithStatsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UserWithoutChatroomDTO getWithoutChatroom(final Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.mapToDTO(user, new UserWithoutChatroomDTO()))
                .orElseThrow(NotFoundException::new);
    }

    // public UserPublicDTO getPublic(final Long id) {
    //     return userRepository.findById(id)
    //             .map(user -> userMapper.mapToDTO(user, new UserPublicDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    public Long create(final UserWithoutChatroomDTO userDTO) {
        final User user = new User();
        userMapper.mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserWithoutChatroomDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        userMapper.mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        user.getChatroomUsers().forEach(chatroomUserRepository::delete);
        userRepository.delete(user);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
