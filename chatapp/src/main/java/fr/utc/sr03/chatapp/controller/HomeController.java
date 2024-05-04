package fr.utc.sr03.chatapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // return "home/index";
        // return "demo";
        return "layouts/master";
    }

}
