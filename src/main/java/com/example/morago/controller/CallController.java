package com.example.morago.controller;

import com.example.morago.model.dto.requests.call.CallCreateRequest;
import com.example.morago.model.dto.requests.call.CallPayload;
import com.example.morago.model.dto.requests.call.CallRateRequest;
import com.example.morago.model.dto.response.calls.RatedCallResponse;
import com.example.morago.model.entity.Call;
import com.example.morago.service.CallService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "CallController")
public class CallController {

    private final CallService callService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<Void> createCall(Authentication authentication,  @RequestBody CallCreateRequest request) {
        Long userId = ((Jwt) authentication.getPrincipal()).getClaim("id");
        CallPayload payload = callService.createCall(userId, request);

        messagingTemplate.convertAndSendToUser(
            String.valueOf(payload.getTranslatorId()),
            "/topic/incoming-call",
            payload
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/rate/{id}")
    public ResponseEntity<RatedCallResponse> rateCall(
            @PathVariable Long id,
            Authentication authentication,
            @RequestBody @Valid CallRateRequest request) {

        Long raterId = ((Jwt) authentication.getPrincipal()).getClaim("id");
        return ResponseEntity.ok(callService.rateCall(id, raterId, request));
    }
}

