package com.example.morago.controller;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.controller.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.controller.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.file.FileService;
import com.example.morago.service.userProfile.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;
    private final FileService fileService;
    private final UserRepository userRepository;

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

    // Загрузка аватара
    @PostMapping("/image")
    @Operation(description = "Upload avatar image")
    public File uploadAvatarImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        File uploadedFile;
        if (user.getImageFile() != null) {
            uploadedFile = fileService.uploadFile(file, FileType.AVATAR, user.getImageFile().getId());
        } else {
            uploadedFile = fileService.uploadFile(file, FileType.AVATAR, null);
        }
        user.setImageFile(uploadedFile);
        userRepository.save(user);
        return uploadedFile;
    }

    // Удаление аватара
    @DeleteMapping("/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatarImage(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getImageFile() != null) {
            fileService.deleteFile(user.getImageFile().getId());
            user.setImageFile(null);
            userRepository.save(user);
        }
    }
}
