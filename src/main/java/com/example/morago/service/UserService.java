package com.example.morago.service;

import com.example.morago.controller.dto.requests.auth.UserCreateRequest;
import com.example.morago.exception.HandledException;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.TranslatorRepository;
import com.example.morago.repository.UserProfileRepository;
import com.example.morago.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final TranslatorRepository translatorRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfile createUserProfile(UserCreateRequest request) {
        checkIsExistByPhone(request.getPhone());

        UserProfile userProfile = UserProfile
            .builder()
            .phone(request.getPhone())
            .password(passwordEncoder.encode(request.getPassword()))
            .isActive(true)
            .isDebtor(false)
            .isFreeCallMade(false)
            .balance(1000L)
            .build();

        userProfileRepository.save(userProfile);
        return userProfile;
    }

    public Translator createTranslator(UserCreateRequest request) {
        checkIsExistByPhone(request.getPhone());

        Translator translator = Translator
            .builder()
            .phone(request.getPhone())
            .password(passwordEncoder.encode(request.getPassword()))
            .isActive(true)
            .balance(0L)
            .build();

        translatorRepository.save(translator);
        return translator;
    }

    public void checkIsExistByPhone(String phone) {
        if (repository.existsByPhone(phone)) {
            throw new HandledException("User profile already exists");
        }
    }
}
