package com.example.morago.controller.translatorController;

import com.example.morago.model.dto.requests.translator.TranslatorUpdateRequest;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.TranslatorService;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/translator")
@RequiredArgsConstructor
public class TranslatorController {

    private final TranslatorService translatorService;
    private final FileService fileService;
    private final UserRepository userRepository;

    @PutMapping()
    @Operation(description = "Update translator")
    public ResponseEntity<Translator> updateTranslator(
            @RequestBody TranslatorUpdateRequest request) {
        return ResponseEntity.ok(translatorService.update(request));
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
