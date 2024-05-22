package fr.utc.sr03.chatapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class HomeController {

    @GetMapping("/")
    public String index() {
        // return "index";
        return "demo";
        // return "home/index2";
    }

}
