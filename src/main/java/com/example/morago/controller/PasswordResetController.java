package com.example.morago.controller;

import com.example.morago.model.dto.requests.auth.passwordReset.OtpVerificationRequest;
import com.example.morago.model.dto.requests.auth.passwordReset.PasswordResetConfirmRequest;
import com.example.morago.model.dto.requests.auth.passwordReset.PasswordResetRequest;
import com.example.morago.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publicResetPassword")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService service;

    @PostMapping("/reset/request")
    public ResponseEntity<Void> request(@RequestBody PasswordResetRequest request) {
        service.requestReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset/verify")
    public ResponseEntity<String> verify(@RequestBody OtpVerificationRequest req) {
        String resetToken = service.verifyCode(req);
        return ResponseEntity.ok(resetToken);
    }

    @PostMapping("/reset/confirm")
    public ResponseEntity<String> confirm(@RequestBody PasswordResetConfirmRequest request) {
        service.confirmPasswordReset(request);
        return ResponseEntity.ok().build();
    }
}
