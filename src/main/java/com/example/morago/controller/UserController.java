package com.example.morago.controller;

import com.example.morago.controller.dto.requests.user.UserProfileCreateRequest;
import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.requests.user.UserProfileUpdateBalanceRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.service.userProfile.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService service;

    @GetMapping()
    @Operation(description = "Get list of users with filters")
    public ResponseEntity<Page<UserGetResponse>> getUserProfiles(
        @ModelAttribute UserGetRequest request) {
        return ResponseEntity.ok(service.searchUsers(request));
    }

    @GetMapping("/{id}")
    @Operation(description = "Get user by id")
    public ResponseEntity<UserProfile> getUserProfile(
        @PathVariable("id")
        @Parameter(description = "User Id", example = "6")
        Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    @Operation(description = "Create user")
    public ResponseEntity<UserProfile> createUserProfile(
        @RequestBody UserProfileCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PatchMapping()
    @Operation(description = "Update user balance")
    public ResponseEntity<UserProfile> updateBalance(@RequestBody UserProfileUpdateBalanceRequest request) {
        return ResponseEntity.ok(service.updateBalance(request));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<Void> deleteUserProfile(
        @PathVariable("id") @Parameter(description = "User Id", example = "6") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
