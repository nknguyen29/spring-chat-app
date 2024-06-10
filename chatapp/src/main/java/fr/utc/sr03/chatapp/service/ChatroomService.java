package fr.utc.sr03.chatapp.service;

import fr.utc.sr03.chatapp.domain.Chatroom;
import fr.utc.sr03.chatapp.domain.ChatroomUser;
import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.mapper.ChatroomMapper;
import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.model.ChatroomPostDTO;
import fr.utc.sr03.chatapp.model.ChatroomPublicDTO;
import fr.utc.sr03.chatapp.model.ChatroomUserPostDTO;
import fr.utc.sr03.chatapp.model.ChatroomWithoutUserDTO;
import fr.utc.sr03.chatapp.repos.ChatroomRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import fr.utc.sr03.chatapp.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;

    private final ChatroomMapper chatroomMapper;

    public ChatroomService(final ChatroomRepository chatroomRepository,
            final UserRepository userRepository,
            final ChatroomMapper chatroomMapper) {
        this.chatroomRepository = chatroomRepository;
        this.userRepository = userRepository;

        this.chatroomMapper = chatroomMapper;
    }

    public List<ChatroomDTO> findAll() {
        final List<Chatroom> chatrooms = chatroomRepository.findAll(Sort.by("id"));
        return chatrooms.stream()
                .map(chatroom -> chatroomMapper.mapToDTO(chatroom, new ChatroomDTO()))
                .toList();
    }

    public List<ChatroomPublicDTO> findAllPublic() {
        final List<Chatroom> chatrooms = chatroomRepository.findAll(Sort.by("id"));
        return chatrooms.stream()
                .map(chatroom -> chatroomMapper.mapToDTO(chatroom, new ChatroomPublicDTO()))
                .toList();
    }

    public ChatroomDTO get(final Long id) {
        return chatroomRepository.findById(id)
                .map(chatroom -> chatroomMapper.mapToDTO(chatroom, new ChatroomDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public ChatroomPublicDTO getPublic(final Long id) {
        return chatroomRepository.findById(id)
                .map(chatroom -> chatroomMapper.mapToDTO(chatroom, new ChatroomPublicDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ChatroomUserPostDTO chatroomUserDTO) {
        chatroomUserDTO.getUserIds().forEach(userId -> {
            userRepository.findById(userId)
                    .orElseThrow(NotFoundException::new);
        });

        final Chatroom chatroom = new Chatroom();
        chatroomMapper.mapToEntity(chatroomUserDTO.getChatroomDTO(), chatroom);
        chatroomRepository.save(chatroom);

        chatroomUserDTO.getUserIds().forEach(userId -> {
            final User user = userRepository.findById(userId)
                    .orElseThrow(NotFoundException::new);
            final ChatroomUser chatroomUser = new ChatroomUser();
            chatroomUser.setChatroom(chatroom);
            chatroomUser.setUser(user);
            chatroom.getChatroomUsers().add(chatroomUser);
        });
        chatroomRepository.save(chatroom);

        return chatroom.getId();
    }

    // public void update(final Long id, final ChatroomDTO chatroomDTO) {
    //     final Chatroom chatroom = chatroomRepository.findById(id)
    //             .orElseThrow(NotFoundException::new);
    //     mapToEntity(chatroomDTO, chatroom);
    //     chatroomRepository.save(chatroom);
    // }

    public void delete(final Long id) {
        chatroomRepository.deleteById(id);
    }

}
