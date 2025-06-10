package com.example.morago.service.userProfile;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.requests.user.UserProfileCreateRequest;
import com.example.morago.controller.dto.requests.user.UserProfileUpdateBalanceRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.exception.HandledException;
import com.example.morago.exception.NotFoundMessage;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.repository.FileRepository;
import com.example.morago.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repository;
    private final FileRepository fileRepository;

    @Override
    public UserProfile create(UserProfileCreateRequest request) {
        File image = null;
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            image = fileRepository.findByPath(request.getImageUrl())
                .orElseThrow(() -> new HandledException(NotFoundMessage.IMAGE.format()));
        }

        return repository.save(request.build(image));
    }

    @Override
    public UserProfile updateBalance(UserProfileUpdateBalanceRequest request) {

        UserProfile user = repository.findByPhoneAndFirstNameIgnoreCaseAndLastNameIgnoreCase(
            request.getPhone(),
            request.getFirstName(),
            request.getLastName()
        ).orElseThrow(() -> new HandledException(NotFoundMessage.USER.format()));

        user.setBalance(request.getCoins());
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        UserProfile userProfile = findById(id);

        if (userProfile == null) {
            throw new HandledException(NotFoundMessage.USER.format());
        }

        repository.delete(userProfile);
        log.info("User {} успешно удален!", userProfile.getFullName());
    }

    @Override
    public Page<UserGetResponse> searchUsers(UserGetRequest request) {
        Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Page<UserProfile> users = repository.findByName(request.getNameUserOrCompany(), pageable);

        return users.map(this::mapToDto);
    }

    @Override
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