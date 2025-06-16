package com.example.morago.service.userProfile;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.UserProfile;
import org.springframework.data.domain.Page;

public interface UserProfileService {

    UserProfile update(UserProfileUpdateRequest request);

    void delete(Long id);

    UserProfile findById(Long id);

//    List<UserProfile> findAll();

    Page<UserGetResponse> searchUsers(UserGetRequest request);
}