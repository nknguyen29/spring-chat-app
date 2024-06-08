package fr.utc.sr03.chatapp.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.utc.sr03.chatapp.model.TestDTO;


@RestController
@RequestMapping("/api/test")
public class TestResource {

    @GetMapping
    public ResponseEntity<TestDTO> test() {
        return ResponseEntity.ok(new TestDTO("Hello World!"));
    }

    @GetMapping("/logged")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> testLogged() {
        return ResponseEntity.ok("You are logged in!");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("You are an admin!");
    }

}
