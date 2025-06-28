package com.example.morago.controller;

import com.example.morago.controller.dto.requests.auth.AuthRequest;
import com.example.morago.controller.dto.requests.auth.UserCreateRequest;
import com.example.morago.controller.dto.response.auth.AuthResponse;
import com.example.morago.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register/user")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse registerUser(@Valid @RequestBody UserCreateRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/register/translator")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse registerTranslator(@Valid @RequestBody UserCreateRequest request) {
        return authService.registerTranslator(request);
    }
}
