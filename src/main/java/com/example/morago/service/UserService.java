package com.example.morago.service;

import com.example.morago.model.entity.base.User;
import com.example.morago.util.exception.HandledException;
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

    public User getUserByPhone(String phone) {
        return repository.findByPhone(phone)
            .orElseThrow(()->new HandledException("User profile not found"));
    }
}
