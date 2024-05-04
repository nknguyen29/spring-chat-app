package fr.utc.sr03.chatapp.rest;

import fr.utc.sr03.chatapp.model.ChatroomDTO;
import fr.utc.sr03.chatapp.service.ChatroomService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/chatrooms", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatroomResource {

    // private final ChatroomService chatroomService;

    // public ChatroomResource(final ChatroomService chatroomService) {
    //     this.chatroomService = chatroomService;
    // }

    // @GetMapping
    // public ResponseEntity<List<ChatroomDTO>> getAllChatrooms() {
    //     return ResponseEntity.ok(chatroomService.findAll());
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<ChatroomDTO> getChatroom(@PathVariable(name = "id") final Long id) {
    //     return ResponseEntity.ok(chatroomService.get(id));
    // }

    // @PostMapping
    // @ApiResponse(responseCode = "201")
    // public ResponseEntity<Long> createChatroom(@RequestBody @Valid final ChatroomDTO chatroomDTO) {
    //     final Long createdId = chatroomService.create(chatroomDTO);
    //     return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Long> updateChatroom(@PathVariable(name = "id") final Long id,
    //         @RequestBody @Valid final ChatroomDTO chatroomDTO) {
    //     chatroomService.update(id, chatroomDTO);
    //     return ResponseEntity.ok(id);
    // }

    // @DeleteMapping("/{id}")
    // @ApiResponse(responseCode = "204")
    // public ResponseEntity<Void> deleteChatroom(@PathVariable(name = "id") final Long id) {
    //     chatroomService.delete(id);
    //     return ResponseEntity.noContent().build();
    // }

}
