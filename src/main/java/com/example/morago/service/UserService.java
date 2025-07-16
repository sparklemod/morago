package com.example.morago.service;

import com.example.morago.model.entity.base.User;
import com.example.morago.repository.UserRepository;
import com.example.morago.util.exception.HandledException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

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
}
