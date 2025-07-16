package com.example.morago.controller;

import com.example.morago.model.dto.requests.notification.NotificationMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "NotificationController")
public class NotificationController {

    private SimpMessagingTemplate messagingTemplate;

    //TODO реализовать методы
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public NotificationMessage notifyAll(NotificationMessage message) {
        return message;
    }
}
