package com.example.morago.controller.userController;

import com.example.morago.model.dto.requests.user.UpdateNameSurnameRequest;
import com.example.morago.model.dto.requests.user.UpdatePasswordRequest;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.UserProfileService;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;
    private final FileService fileService;
    private final UserRepository userRepository;

    @PutMapping("/name")
    public ResponseEntity<Void> updateName(@AuthenticationPrincipal User currentUser,
        @RequestBody UpdateNameSurnameRequest request) {
        service.updateName(currentUser.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal User currentUser,
        @RequestBody UpdatePasswordRequest request) {
        service.updatePassword(currentUser.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam String phoneOrEmail) {
        service.resetPassword(phoneOrEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(description = "Get user by id")
    public ResponseEntity<UserGetResponse> getUserProfile(
            @PathVariable("id")
            @Parameter(description = "User Id", example = "6")
            Long id) {
        return ResponseEntity.ok(service.mapToDto(service.findById(id)));
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
