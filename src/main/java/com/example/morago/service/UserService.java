package com.example.morago.service;

import com.example.morago.exception.HandledException;
import com.example.morago.repository.UserRepository;
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
}
