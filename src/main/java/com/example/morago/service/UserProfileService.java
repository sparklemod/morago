package com.example.morago.service;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.util.exception.HandledException;
import com.example.morago.util.exception.enums.NotFoundMessage;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.UserProfileRepository;
import com.example.morago.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository repository;
    private final FileService fileService;

    //В макете у пользователя при регистрации нет имени фамилии
    //предполагаю что апдейт есть где-то после регистрации
    public UserProfile update(UserProfileUpdateRequest request) {
        File image = fileService.getFileByUrl(request.getImageUrl());
        return repository.save(request.build(image));
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