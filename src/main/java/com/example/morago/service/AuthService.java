package com.example.morago.service;

import com.example.morago.config.security.JwtUtil;
import com.example.morago.controller.dto.requests.auth.AuthRequest;
import com.example.morago.controller.dto.requests.auth.UserCreateRequest;
import com.example.morago.controller.dto.response.auth.AuthResponse;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.entity.base.User;
import com.example.morago.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getPhone(), authRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        return buildAuthResponse(user);
    }

    public AuthResponse registerUser(UserCreateRequest authRequest) {
        userService.checkIsExistByPhone(authRequest.getPhone());

        UserProfile userProfile = new UserProfile();
        userProfile.setPhone(authRequest.getPhone());
        userProfile.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        userProfile.setIsActive(true);
        userProfile.setBalance(0L);
        // firstName, lastName, email, imageId, notifications остаются null
        User savedUser = userRepository.save(userProfile);
        return buildAuthResponse(savedUser);
    }

    public AuthResponse registerTranslator(UserCreateRequest authRequest) {
        userService.checkIsExistByPhone(authRequest.getPhone());

        Translator translator = new Translator();
        translator.setPhone(authRequest.getPhone());
        translator.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        translator.setIsActive(false);
        translator.setBalance(0L);
        // firstName, lastName, email, imageId, notifications остаются null
        User savedUser = userRepository.save(translator);
        return buildAuthResponse(savedUser);
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtUtil.generateToken(user);
        String role = user.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(role)
                .build();
    }
}
