package com.example.morago.controller;

import com.example.morago.config.security.JwtUtil;
import com.example.morago.controller.dto.requests.auth.AuthRequest;
import com.example.morago.controller.dto.requests.auth.UserCreateRequest;
import com.example.morago.controller.dto.response.auth.AuthResponse;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getPhone(),
                        authRequest.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getPhone());
        User user = (User) userDetails;
        String token = jwtUtil.generateToken((User) userDetails);
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/user")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserCreateRequest user) {
        UserProfile createdUser = userService.createUserProfile(user);
        String token = jwtUtil.generateToken(createdUser);
        AuthResponse response = AuthResponse.builder()
                .token(token)
                .id(createdUser.getId())
                .phone(createdUser.getPhone())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/translator")
    @Operation(description = "Create translator")
    public ResponseEntity<AuthResponse> registerTranslator(@RequestBody UserCreateRequest user) {
        Translator createdUser = userService.createTranslator(user);
        String token = jwtUtil.generateToken(createdUser);
        AuthResponse response = AuthResponse.builder()
            .token(token)
            .id(createdUser.getId())
            .phone(user.getPhone())
            .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
