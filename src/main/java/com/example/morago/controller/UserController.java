package com.example.morago.controller;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping
    @Operation(
        description = "<strong>Get a list of users by request body parameters</strong>"
    )
    public ResponseEntity<Page<UserGetResponse>> getUsers(@ModelAttribute UserGetRequest request) {
        return ResponseEntity.ok(userProfileService.searchUsers(request));
    }
}
