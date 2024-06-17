package fr.utc.sr03.chatapp.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.utc.sr03.chatapp.model.UserDTO;
import fr.utc.sr03.chatapp.model.UserPublicDTO;
import fr.utc.sr03.chatapp.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;

    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/public")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserPublicDTO>> getAllPublicUsers() {
        return ResponseEntity.ok(userService.findAllPublic());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping("{id}/public")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserPublicDTO> getPublicUser(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userService.getPublic(id));
    }

    // @PostMapping
    // @ApiResponse(responseCode = "201")
    // public ResponseEntity<Long> createUser(@RequestBody @Valid final UserAddDTO userDTO) {
    //     final Long createdId = userService.create(userDTO);
    //     return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Long> updateUser(@PathVariable(name = "id") final Long id,
    //         @RequestBody @Valid final UserDTO userDTO) {
    //     userService.update(id, userDTO);
    //     return ResponseEntity.ok(id);
    // }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") final Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/lock")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> lockUser(@PathVariable(name = "id") final Long id) {
        userService.lock(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/unlock")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> unlockUser(@PathVariable(name = "id") final Long id) {
        userService.unlock(id);
        return ResponseEntity.noContent().build();
    }

}
