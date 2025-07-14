package com.example.morago.controller;

import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final UserRepository userRepository;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type")FileType type) {
        return fileService.uploadFile(file, type, null);
    }

    @PostMapping("/avatar/upload")
    @Operation(description = "Upload avatar image [TRANSLATOR, USER]")
    public File uploadAvatar(@RequestParam("file") MultipartFile file, Authentication authentication) {
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

    @DeleteMapping("/avatar/delete")
    @Operation(description = "Delete avatar image [TRANSLATOR, USER]")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatar(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getImageFile() != null) {
            fileService.deleteFile(user.getImageFile().getId());
            user.setImageFile(null);
            userRepository.save(user);
        }
    }

    //ADMIN
    @GetMapping("/admin/files")
    @Operation(description = "Get all files [ADMIN]")
    public PageResponse<File> getAllFiles(@Valid @ModelAttribute PageRequest pageRequest) {
        Page<File> filePage = fileService.getAllFiles(pageRequest);
        return new PageResponse<>(filePage);
    }

    @GetMapping("/admin/files/{id}")
    @Operation(description = "Get file by Id [ADMIN]")
    public File getFile(@PathVariable Long id) {
        return fileService.getFileById(id);
    }

    @DeleteMapping("/admin/files/{id}")
    @Operation(description = "Delete file by Id [ADMIN]")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
