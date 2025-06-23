package com.example.morago.controller;

import com.example.morago.controller.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.controller.dto.requests.translator.TranslatorUpdateRequest;
import com.example.morago.controller.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.entity.File;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.FileType;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.TranslatorService;
import com.example.morago.service.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/translators")
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

    @GetMapping
    @Operation(
            description = "<strong>Get a list of translators by request body parameters</strong>"
    )
    public ResponseEntity<Page<TranslatorGetResponse>> getTranslators(@ModelAttribute TranslatorGetRequest request) {
        return ResponseEntity.ok(translatorService.searchTranslators(request));
    }

    @GetMapping("/{id}")
    @Operation(
            description = "<strong>Get translator by ID</strong>"
    )
    public ResponseEntity<Translator> getTranslatorById(
            @PathVariable("id")
            @Parameter(description = "Translator ID", example = "2")
            Long id) {
        return ResponseEntity.ok(translatorService.searchUserById(id));
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
