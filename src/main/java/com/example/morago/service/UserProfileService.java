package com.example.morago.service;

import com.example.morago.model.dto.requests.user.UpdateNameSurnameRequest;
import com.example.morago.model.dto.requests.user.UpdatePasswordRequest;
import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.util.exception.HandledException;
import com.example.morago.util.exception.enums.NotFoundMessage;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;


    public void updateName(Long userId, UpdateNameSurnameRequest request) {
        UserProfile user = findById(userId);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        repository.save(user);
    }

    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        UserProfile user = findById(userId);

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Passwords don't match");
        }

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("New passwords don't match");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        repository.save(user);
    }

    //TODO спросить у фронта
    public void resetPassword(String phone) {
    }

    public UserProfile update(UserProfileUpdateRequest request) {
        UserProfile user = findById(request.getId());
        return repository.save(request.build(user));
    }

    public void delete(Long id) {
        UserProfile userProfile = findById(id);
        repository.delete(userProfile);
        log.info("User {} успешно удален!", userProfile.getFullName());
    }

    public Page<UserGetResponse> searchUsers(UserGetRequest request) {
        Page<UserProfile> users = repository.findByKeyword(request.getKeyword(), request.toPageable());

        return users.map(this::mapToDto);
    }

    public UserProfile findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new HandledException(NotFoundMessage.USER.format()));
    }

    private UserGetResponse mapToDto(UserProfile userProfile) {
        return new UserGetResponse(
            userProfile.getId(),
            userProfile.getFirstName(),
            userProfile.getLastName(),
            userProfile.getPhone(),
            userProfile.getEmail(),
            userProfile.getBalance()
        );
    }
}