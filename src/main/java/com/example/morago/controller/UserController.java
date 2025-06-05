package com.example.morago.controller;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping()
    @Operation(
        description = "<strong>Get a list of users by request body parameters</strong>"
    )
    public ResponseEntity<Page<UserGetResponse>> getUsers(@RequestBody UserGetRequest request) {
        return ResponseEntity.ok(userProfileService.searchUsers(request));
    }

    @GetMapping("/{id}")
    @Operation(
        description = "<strong>Get user by id</strong>"
    )
    public ResponseEntity<UserProfile> getUserById(
        @PathVariable("id")
        @Parameter(description = "User Id", example = "6")
        Long id) {
        return ResponseEntity.ok(userProfileService.searchUserById(id));
    }
}
