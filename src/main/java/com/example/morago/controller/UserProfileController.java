package com.example.morago.controller;

import com.example.morago.model.dto.requests.PageRequest;
import com.example.morago.model.dto.requests.transactions.TransactionCreateRequest;
import com.example.morago.model.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.model.dto.response.translator.TranslatorGetByThemesResponse;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.Translator;
import com.example.morago.service.DepositService;
import com.example.morago.service.TranslatorService;
import com.example.morago.service.UserProfileService;
import com.example.morago.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "UserProfileController")
public class UserProfileController {

    private final UserProfileService service;
    private final DepositService depositService;
    private final TranslatorService translatorService;
    private final UserService userService;


    @GetMapping("/translators")
    @Operation(description = "Get translators list filtered by theme")
    public ResponseEntity<Page<TranslatorGetByThemesResponse>> getTranslators(
            @RequestParam(value = "themeId", required = false) Long themeId,
            PageRequest request
    ) {
        return ResponseEntity.ok(translatorService.searchTranslatorsByTheme(themeId, request));
    }

    @GetMapping("/translators/{translatorId}")
    @Operation(description = "Get translator info by Id")
    public ResponseEntity<TranslatorGetResponse> getTranslator(@PathVariable Long translatorId) {
        Translator translator = translatorService.findById(translatorId);
        return ResponseEntity.ok(TranslatorGetResponse.mapToDto(translator));
    }

    @PostMapping("/deposit")
    @Operation(description = "Create deposit")
    public ResponseEntity<Deposit> createDeposit(Authentication authentication,
                                                 @RequestBody TransactionCreateRequest request) {
        Long userId = userService.extractUserId(authentication);
        Deposit deposit = depositService.createDeposit(userId, request);
        return ResponseEntity.ok(deposit);
    }

    @PutMapping()
    @Operation(description = "Update user profile")
    public ResponseEntity<UserGetResponse> updateUserProfile(
            Authentication authentication,
            @RequestBody UserProfileUpdateRequest request) {
        Long userId = userService.extractUserId(authentication);
        return ResponseEntity.ok(UserGetResponse.mapToDto(service.update(userId, request)));
    }
}
