package com.example.morago.service;

import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.repository.specification.UserSpecification;
import com.example.morago.util.exception.HandledException;
import com.example.morago.util.exception.enums.NotFoundMessage;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfile update(Long id, UserProfileUpdateRequest request) {
        UserProfile user = findById(id);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        repository.save(user);
        return repository.save(user);
    }

    public void delete(Long id) {
        UserProfile userProfile = findById(id);
        repository.delete(userProfile);
        log.info("User {} успешно удален!", userProfile.getNameWithSurname());
    }

    public Page<UserGetResponse> searchUsers(UserGetRequest request) {
        Page<UserProfile> users = repository.findAll(
            UserSpecification.build(request),
            request.toPageable()
        );

        return users.map(UserGetResponse::mapToDto);
    }

    public UserProfile findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new HandledException(NotFoundMessage.USER.format()));
    }
}