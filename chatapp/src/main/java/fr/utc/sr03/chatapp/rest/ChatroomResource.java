package fr.utc.sr03.chatapp.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.model.ChatroomPublicDTO;
import fr.utc.sr03.chatapp.model.ChatroomUserPostDTO;
import fr.utc.sr03.chatapp.service.ChatroomService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/chatrooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatroomResource {

    private final ChatroomService chatroomService;

    public ChatroomResource(final ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ChatroomDTO>> getAllChatrooms() {
        return ResponseEntity.ok(chatroomService.findAll());
    }

    @GetMapping("/public")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ChatroomPublicDTO>> getAllPublicChatrooms() {
        return ResponseEntity.ok(chatroomService.findAllPublic());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ChatroomDTO> getChatroom(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chatroomService.get(id));
    }

    @GetMapping("{id}/public")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatroomPublicDTO> getPublicChatroom(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(chatroomService.getPublic(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createChatroom(@RequestBody @Valid final ChatroomUserPostDTO chatroomUserDTO) {
        final Long createdId = chatroomService.create(chatroomUserDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Long> updateChatroom(@PathVariable(name = "id") final Long id,
    //         @RequestBody @Valid final ChatroomDTO chatroomDTO) {
    //     chatroomService.update(id, chatroomDTO);
    //     return ResponseEntity.ok(id);
    // }

    @PutMapping("/{id}/users/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> addUserToChatroom(@PathVariable(name = "id") final Long id,
            @PathVariable(name = "userId") final Long userId) {
        chatroomService.addUser(id, userId);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}/users/{userId}")
    @PreAuthorize("isAuthenticated()")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> removeUserFromChatroom(@PathVariable(name = "id") final Long id,
            @PathVariable(name = "userId") final Long userId) {
        chatroomService.removeUser(id, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChatroom(@PathVariable(name = "id") final Long id) {
        chatroomService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
