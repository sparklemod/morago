package com.example.morago.controller;

import com.example.morago.controller.dto.requests.user.UserProfileCreateRequest;
import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.service.userProfile.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ResponseEntity<?> getUserProfiles(@ModelAttribute UserGetRequest request) {
        try {
            return ResponseEntity.ok(service.searchUsers(request));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "Get user by id")
    public ResponseEntity<?> getUserProfile(
        @PathVariable("id")
        @Parameter(description = "User Id", example = "6")
        Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    @Operation(description = "Create user")
    public ResponseEntity<?> createUserProfile(@RequestBody UserProfileCreateRequest request) {
        try {
            return ResponseEntity.ok(service.create(request));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete user by id")
    public ResponseEntity<?> deleteUserProfile(
        @PathVariable("id")
        @Parameter(description = "User Id", example = "6")
        Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Successfully deleted");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
