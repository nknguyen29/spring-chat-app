package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.repos.ChatroomRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import fr.utc.sr03.chatapp.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;

    public ChatroomService(final ChatroomRepository chatroomRepository,
            final UserRepository userRepository) {
        this.chatroomRepository = chatroomRepository;
        this.userRepository = userRepository;
    }

    // public List<ChatroomDTO> findAll() {
    //     final List<Chatroom> chatrooms = chatroomRepository.findAll(Sort.by("id"));
    //     return chatrooms.stream()
    //             .map(chatroom -> mapToDTO(chatroom, new ChatroomDTO()))
    //             .toList();
    // }

    // public ChatroomDTO get(final Long id) {
    //     return chatroomRepository.findById(id)
    //             .map(chatroom -> mapToDTO(chatroom, new ChatroomDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    // public Long create(final ChatroomDTO chatroomDTO) {
    //     final Chatroom chatroom = new Chatroom();
    //     mapToEntity(chatroomDTO, chatroom);
    //     return chatroomRepository.save(chatroom).getId();
    // }

    // public void update(final Long id, final ChatroomDTO chatroomDTO) {
    //     final Chatroom chatroom = chatroomRepository.findById(id)
    //             .orElseThrow(NotFoundException::new);
    //     mapToEntity(chatroomDTO, chatroom);
    //     chatroomRepository.save(chatroom);
    // }

    // public void delete(final Long id) {
    //     chatroomRepository.deleteById(id);
    // }

    // private ChatroomDTO mapToDTO(final Chatroom chatroom, final ChatroomDTO chatroomDTO) {
    //     chatroomDTO.setId(chatroom.getId());
    //     chatroomDTO.setTitle(chatroom.getTitle());
    //     chatroomDTO.setDescription(chatroom.getDescription());
    //     chatroomDTO.setStartDate(chatroom.getStartDate());
    //     chatroomDTO.setValidityDuration(chatroom.getValidityDuration());
    //     chatroomDTO.setUsers(chatroom.getUsers().stream()
    //             .map(user -> user.getId())
    //             .toList());
    //     return chatroomDTO;
    // }

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
