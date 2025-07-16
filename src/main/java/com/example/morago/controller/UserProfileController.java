package com.example.morago.controller;

import com.example.morago.config.security.userDetails.CustomUserDetails;
import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.dto.requests.user.UserProfileUpdateRequest;
import com.example.morago.model.dto.response.user.UserGetResponse;
import com.example.morago.model.entity.Deposit;
import com.example.morago.service.DepositService;
import com.example.morago.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "UserProfileController")
public class UserProfileController {

    private final UserProfileService service;
    private final DepositService depositService;

    @GetMapping("/themes")
    @Operation(description = "Get current user favorite themes")
    public void getThemes() {
    }

    @GetMapping("/translators")
    @Operation(description = "Get translators list")
    public void getTranslators(@RequestParam("themeId") Long themeId) {
    }

    @GetMapping("/translators/{translatorId}")
    @Operation(description = "Get translator info by Id")
    public void getTranslator(@PathVariable Long translatorId) {
    }

    @PostMapping("/deposit")
    @Operation(description = "Create deposit")
    public ResponseEntity<Deposit> createDeposit(Authentication authentication,
        @RequestBody TransactionCreateRequest request) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Deposit deposit = depositService.createDeposit(principal.getId(), request);
        return ResponseEntity.ok(deposit);
    }

    @PutMapping()
    @Operation(description = "Update user")
    public ResponseEntity<UserGetResponse> updateUserProfile(
        Authentication authentication,
        @RequestBody UserProfileUpdateRequest request) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(service.mapToDto(service.update(principal.getId(), request)));
    }
}
