package com.example.morago.controller;

import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @GetMapping()
    @Operation(description = "Get list of users with filters")
    public ResponseEntity<Page<UserGetResponse>> getUserProfiles(
            @ModelAttribute UserGetRequest request) {
        return ResponseEntity.ok(service.searchUsers(request));
    }

    @GetMapping("/admin/users/{id}")
    @Operation(description = "Get user by id")
    public ResponseEntity<UserGetResponse> getUserProfile(
            @PathVariable("id")
            @Parameter(description = "User Id", example = "6")
            Long id) {
        return ResponseEntity.ok(service.mapToDto(service.findById(id)));
    }

    @PutMapping("/admin/users")
    @Operation(description = "Update user")
    public ResponseEntity<UserGetResponse> updateUserProfile(
            @RequestBody UserProfileUpdateRequest request) {
        return ResponseEntity.ok(service.mapToDto(service.update(request)));
    }

    @DeleteMapping("/admin/users/{id}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable("id") @Parameter(description = "User Id", example = "6") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
