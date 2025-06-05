package com.example.morago.controller;

import com.example.morago.controller.dto.requests.call.CallCreateRequest;
import com.example.morago.model.entity.Call;
import com.example.morago.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calls")
@RequiredArgsConstructor
public class CallController {

    private final CallService callService;

    @PostMapping
    public ResponseEntity<Call> createCall(@RequestBody CallCreateRequest request) {
        return ResponseEntity.ok(callService.createCall(
            request.getCallerId(), request.getRecipientId(), request.getThemeId(),
            request.getChannelName()
        ));
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Call> endCall(@PathVariable Long id) {
        return ResponseEntity.ok(callService.endCall(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Call> getCall(@PathVariable Long id) {
        return ResponseEntity.of(callService.getCall(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCall(@PathVariable Long id) {
        callService.deleteCall(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Call> updateCall(@PathVariable Long id, @RequestBody Call updated) {
        return ResponseEntity.ok(callService.updateCall(id, updated));
    }
}