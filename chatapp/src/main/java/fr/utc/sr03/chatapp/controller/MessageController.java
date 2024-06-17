package fr.utc.sr03.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import fr.utc.sr03.chatapp.model.Message;


@Controller
public class MessageController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public Message receiveMessage(Message message) throws Exception {
        Long sender = message.getSender();
        String content = message.getContent();
        if (sender == null) {
            return null; // ignore the message if the sender is not set
        } else {
            // TODO: check if the sender is a participant of the room
            // if not, return an error message
        }

        if (content == null) {
            content = "";
        }
        message.setContent(HtmlUtils.htmlEscape(content)); // sanitize the message
        return message;
    }

}
