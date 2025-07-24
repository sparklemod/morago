package com.example.morago.service;

import com.example.morago.model.dto.requests.user.UpdatePasswordRequest;
import com.example.morago.model.entity.base.User;
import com.example.morago.repository.UserRepository;
import com.example.morago.util.exception.HandledException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void checkIsExistByPhone(String phone) {
        if (repository.existsByPhone(phone)) {
            throw new HandledException("User profile already exists");
        }
    }

    public User getUserByPhone(String phone) {
        System.out.println("Trying to auth with phone = " + phone);
        repository.findByPhone(phone).ifPresentOrElse(
                u -> System.out.println("✅ FOUND USER: " + u.getPhone()),
                () -> System.out.println("❌ NOT FOUND")
        );

        return repository.findByPhone(phone)
            .orElseThrow(()->new HandledException("User profile not found"));
    }

    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = getUserById(userId);

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("New passwords don't match");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        repository.save(user);
    }

    public User getUserById(Long id) {
        return repository.findById(id)
            .orElseThrow(()->new HandledException("User profile not found"));
    }

    public Long extractUserId(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaim("id");
    }
}
