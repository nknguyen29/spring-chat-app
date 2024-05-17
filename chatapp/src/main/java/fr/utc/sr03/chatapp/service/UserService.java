package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.mapper.UserMapper;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserListDTO;
import fr.utc.sr03.chatapp.model.UserPublicDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import fr.utc.sr03.chatapp.repos.ChatroomUserRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import fr.utc.sr03.chatapp.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
*
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

    public List<UserListDTO> search(
            final String search,
            final String sortBy,
            final String sortOrder,
            final Integer page,
            final Integer size,
            final Boolean showAll,
            final Boolean showAdmins,
            final Boolean showUsers,
            final Boolean showLocked,
            final Boolean showUnlocked 
    ) {
        Pageable sortedByName = PageRequest.of(page, size, Sort.by(sortOrder, sortBy));
        final List<User> users = userRepository.findAll(sortedByName).toList();
        if (search != null && !search.isBlank()) {
            users.removeIf(user -> !user.getFirstName().contains(search) && !user.getLastName().contains(search) && !user.getEmail().contains(search));
        }
        if (!showAll) {
            users.removeIf(user -> (showAdmins && !user.getIsAdmin()) || (showUsers && user.getIsAdmin()));
            users.removeIf(user -> (showLocked && !user.getIsLocked()) || (showUnlocked && user.getIsLocked()));
        }
        return users.stream()
                .map(user -> userMapper.mapToDTO(user, new UserListDTO()))
                .toList();
    }

    // public UserDTO get(final Long id) {
    //     return userRepository.findById(id)
    //             .map(user -> userMapper.mapToDTO(user, new UserDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    // public UserPublicWithStatsDTO getPublicWithStats(final Long id) {
    //     return userRepository.findById(id)
    //             .map(user -> userMapper.mapToDTO(user, new UserPublicWithStatsDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    // public UserWithoutChatroomDTO getWithoutChatroom(final Long id) {
    //     return userRepository.findById(id)
    //             .map(user -> userMapper.mapToDTO(user, new UserWithoutChatroomDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    // public UserPublicDTO getPublic(final Long id) {
    //     return userRepository.findById(id)
    //             .map(user -> userMapper.mapToDTO(user, new UserPublicDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    // public Long create(final UserWithoutChatroomDTO userDTO) {
    //     final User user = new User();
    //     userMapper.mapToEntity(userDTO, user);
    //     return userRepository.save(user).getId();
    // }

    // public void update(final Long id, final UserWithoutChatroomDTO userDTO) {
    //     final User user = userRepository.findById(id)
    //             .orElseThrow(NotFoundException::new);
    //     userMapper.mapToEntity(userDTO, user);
    //     userRepository.save(user);
    // }

    // public void delete(final Long id) {
    //     final User user = userRepository.findById(id)
    //             .orElseThrow(NotFoundException::new);
    //     // remove many-to-many relations at owning side
    //     user.getChatroomUsers().forEach(chatroomUserRepository::delete);
    //     userRepository.delete(user);
    // }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
