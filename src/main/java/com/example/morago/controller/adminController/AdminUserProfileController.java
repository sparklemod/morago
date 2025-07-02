package com.example.morago.controller.adminController;

import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.UserProfile;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserProfileController {

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

    @PutMapping()
    @Operation(description = "Update user")
    public ResponseEntity<UserProfile> updateUserProfile(
            @RequestBody UserProfileUpdateRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable("id") @Parameter(description = "User Id", example = "6") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
