package fr.utc.sr03.chatapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {

    @GetMapping("/chat")
    public String userPage() {
        return "forward:/index.html";
    }
}
