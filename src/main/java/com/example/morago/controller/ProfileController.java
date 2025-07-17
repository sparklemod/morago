package com.example.morago.controller;

import com.example.morago.config.security.userDetails.CustomUserDetails;
import com.example.morago.model.dto.requests.CallHistoryRequest;
import com.example.morago.model.dto.requests.PageRequest;
import com.example.morago.model.dto.requests.user.UpdatePasswordRequest;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.UserProfileService;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("profile")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ProfileController", description = "Access: [TRANSLATOR, USER]")
public class ProfileController {
    private final FileService fileService;
    private final UserRepository userRepository;
    private final UserProfileService service;

    @GetMapping("/balance")
    @Operation(description = "Get current user balance")
    public void getBalance() {
    }

    @GetMapping("/calls/history")
    @Operation(description = "Get current user call history")
    public void getCallHistory(Authentication authentication, CallHistoryRequest req) {
    }

    @GetMapping("/notifications")
    @Operation(description = "Get current user notifications")
    public void getNotifications(Authentication authentication, PageRequest req) {
    }

    @PostMapping("/notifications/clear")
    @Operation(description = "Clear all current user notifications")
    public void clearNotifications(Authentication authentication, PageRequest req) {
    }

    //TODO Саша посмотри
    @PostMapping("/password/reset")
    @Operation(description = "Reset password")
    public void resetPassword() {
    }

    //TODO Саша посмотри
    @PostMapping("/password/update")
    @Operation(description = "Update password")
    public void updatePassword(UpdatePasswordRequest request) {
    }

    //TODO Саша посмотри, нужно достать пользователя из jwt и перенести в сервис все
    @PostMapping("/avatar/upload")
    @Operation(description = "Upload avatar image")
    public File uploadAvatar(
            @Parameter(description = "Файл аватара", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();
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

    //TODO Саша посмотри, нужно достать пользователя из jwt и перенести в сервис все
    @DeleteMapping("/avatar/delete")
    @Operation(description = "Delete avatar image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatar(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (user.getImageFile() != null) {
            fileService.deleteFile(user.getImageFile().getId());
            user.setImageFile(null);
            userRepository.save(user);
        }
    }
}
