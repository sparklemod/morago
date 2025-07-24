package com.example.morago.controller;

import com.example.morago.model.dto.requests.call.CallPayload;
import com.example.morago.model.dto.requests.websocket.call.CallRequest;
import com.example.morago.service.CallService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "WebsocketController")
public class WebsocketController {

    private final CallService callService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/call/accept")
    public void acceptCall(@Payload CallRequest request, Principal principal)
        throws AccessDeniedException {
        Long userId = Long.valueOf(principal.getName());

        if (callService.isNotRecipient(userId, request.callId())) {
            throw new AccessDeniedException("User not authorized to accept this call");
        }

        CallPayload payload = callService.acceptCall(request.callId());

        messagingTemplate.convertAndSendToUser(
            String.valueOf(payload.getCallerId()),
            "/topic/call-started",
            payload
        );

        messagingTemplate.convertAndSendToUser(
            String.valueOf(payload.getTranslatorId()),
            "/topic/call-started",
            payload
        );
    }

    @MessageMapping("/call/reject")
    public void rejectCall(@Payload CallRequest request, Principal principal)
        throws AccessDeniedException {
        Long userId = Long.valueOf(principal.getName());

        if (callService.isNotRecipient(userId, request.callId())) {
            throw new AccessDeniedException("User not authorized to accept this call");
        }

        CallPayload payload = callService.rejectCall(request.callId());

        messagingTemplate.convertAndSendToUser(
            String.valueOf(payload.getCallerId()),
            "/topic/call-rejected",
            new SimpleMessage("Call was rejected")
        );
    }


    @MessageMapping("/call/end")
    public void endCall(@Payload CallRequest request) {
        CallPayload payload = callService.endCall(request.callId());

        messagingTemplate.convertAndSendToUser(
            String.valueOf(payload.getCallerId()),
            "/topic/call-ended",
            new SimpleMessage("Call is ended")
        );

        messagingTemplate.convertAndSendToUser(
            String.valueOf(payload.getTranslatorId()),
            "/topic/call-ended",
            new SimpleMessage("Call is ended")
        );
    }
}

