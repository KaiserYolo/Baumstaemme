package com.baumstaemme.backend.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public WebSocketDto sendMessage(@Payload WebSocketDto message) {

        return message;
    }
}
