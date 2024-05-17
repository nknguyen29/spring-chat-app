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
import jakarta.validation.constraints.NotNull;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    public Page<UserListDTO> search(
            final String search,
            final String sortBy,
            final String sortOrder,
            final Integer page,
            final Integer size,
            final Boolean isAdmin,
            final Boolean isLocked
    ) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        if (search == null || search.isBlank()) {
            if (isAdmin == null && isLocked == null) {
                return userRepository.findAll(pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else if (isAdmin == null) {
                return userRepository.findAllByIsLocked(isLocked, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else if (isLocked == null) {
                return userRepository.findAllByIsAdmin(isAdmin, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else {
                return userRepository.findAllByIsAdminAndIsLocked(isAdmin, isLocked, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            }
        } else {
            if (isAdmin == null && isLocked == null) {
                return userRepository.findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCase(
                        search, search, search, null, null, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else if (isAdmin == null) {
                return userRepository.findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsLocked(
                        search, search, search, null, isLocked, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else if (isLocked == null) {
                return userRepository.findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsAdmin(
                        search, search, search, isAdmin, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else {
                return userRepository.findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsAdminAndIsLocked(
                        search, search, search, isAdmin, isLocked, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            }
        }
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
