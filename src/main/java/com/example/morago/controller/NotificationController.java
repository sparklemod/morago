package com.example.morago.controller;

import com.example.morago.controller.dto.websocket.CallPayload;
import com.example.morago.controller.dto.websocket.NotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class NotificationController {

    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public NotificationMessage notifyAll(NotificationMessage message) {
        return message;
    }

    @MessageMapping("/call")
    public void callUser(CallPayload payload) {
        messagingTemplate.convertAndSendToUser(
            payload.getTo(),
            "/topic/incoming-call",
            payload
        );
    }
}
