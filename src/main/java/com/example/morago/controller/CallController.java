package com.example.morago.controller;

import com.example.morago.model.dto.requests.call.CallCreateRequest;
import com.example.morago.model.entity.Call;
import com.example.morago.service.CallService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @PostMapping("start")
    @SendTo
    public ResponseEntity<Call> createCall(@RequestBody CallCreateRequest request) {
        Call call = callService.createCall(request);

        return ResponseEntity.ok(call);
    }

    //TODO реализоавть эти методы
    @PutMapping("/accept/{id}")
    public ResponseEntity<Call> acceptCall(@PathVariable Long id) {
        return ResponseEntity.ok(callService.acceptCall(id));
    }

    @PutMapping("/rate/{id}")
    public ResponseEntity<Call> rateCall(@PathVariable Long id) {
        return ResponseEntity.ok(callService.rateCall(id));
    }

    @PostMapping("/end/{id}")
    public ResponseEntity<Call> endCall(@PathVariable Long id) {
        return ResponseEntity.ok(callService.endCall(id));
    }
}

