package com.example.morago.controller;

import com.example.morago.model.dto.requests.auth.AuthRequest;
import com.example.morago.model.dto.requests.auth.UserCreateRequest;
import com.example.morago.model.dto.response.auth.AuthResponse;
import com.example.morago.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Access: public")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.auth(request.getPhone(), request.getPassword());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody UserCreateRequest request) {
        return authService.register(request);
    }
}
