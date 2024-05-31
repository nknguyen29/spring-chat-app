package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.mapper.UserMapper;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.model.UserPostDTO;
import fr.utc.sr03.chatapp.model.UserSearch;
import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserListDTO;
import fr.utc.sr03.chatapp.model.UserGetDTO;
import fr.utc.sr03.chatapp.model.UserWithoutChatroomDTO;
import fr.utc.sr03.chatapp.repos.ChatroomUserRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import fr.utc.sr03.chatapp.util.NotFoundException;
import jakarta.validation.constraints.NotNull;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ChatroomUserRepository chatroomUserRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository,
            final ChatroomUserRepository chatroomUserRepository,
            final UserMapper userMapper,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.chatroomUserRepository = chatroomUserRepository;

        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> userMapper.mapToDTO(user, new UserDTO()))
                .toList();
    }

    public Page<UserListDTO> findAll(final UserSearch userSearch) {
        final String search = userSearch.getSearch();
        final String sortBy = userSearch.getSortBy();
        final String sortOrder = userSearch.getSortOrder();
        final Integer page = userSearch.getPage();
        final Integer size = userSearch.getSize();
        final Boolean isAdmin = userSearch.isAdmin();
        final Boolean isLocked = userSearch.isLocked();

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
                        search, search, search, pageable)
                        .map(user -> userMapper.mapToDTO(user, new UserListDTO()));
            } else if (isAdmin == null) {
                return userRepository.findAllByFirstNameContainingOrLastNameContainingOrEmailContainingIgnoreCaseAndIsLocked(
                        search, search, search, isLocked, pageable)
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

    public UserGetDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.mapToDTO(user, new UserGetDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UserPostDTO edit(final Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.mapToDTO(user, new UserPostDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserPostDTO userDTO) {
        final User user = new User();
        userMapper.mapToEntity(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserPostDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        userMapper.mapToEntity(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    public void delete(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        user.getChatroomUsers().forEach(chatroomUserRepository::delete);
        userRepository.delete(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void lock(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        user.setIsLocked(true);
        user.setLockedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    public void unlock(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        user.setIsLocked(false);
        user.setLockedAt(null);
        userRepository.save(user);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
