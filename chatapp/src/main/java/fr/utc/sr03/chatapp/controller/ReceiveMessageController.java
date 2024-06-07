package fr.utc.sr03.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import fr.utc.sr03.chatapp.domain.ReceiveMessage;
import fr.utc.sr03.chatapp.domain.SendMessage;

@Controller
public class ReceiveMessageController {


  @MessageMapping("/chat/{roomId}")
  @SendTo("/topic/{roomId}")
  public ReceiveMessage receiveMessage(SendMessage message) throws Exception {
    return new ReceiveMessage("Message : " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

}
