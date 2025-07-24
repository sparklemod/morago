package com.example.morago.controller;

import com.example.morago.model.dto.requests.transactions.TransactionCreateRequest;
import com.example.morago.model.dto.requests.translator.TranslatorUpdateRequest;
import com.example.morago.model.dto.response.translator.TranslatorGetResponse;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.service.TranslatorService;
import com.example.morago.service.UserService;
import com.example.morago.service.WithdrawalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/translator")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "TranslatorController")
public class TranslatorController {

    private final TranslatorService translatorService;
    private final WithdrawalService withdrawalService;
    UserService userService;

    @PutMapping()
    @Operation(description = "Fill translator profile")
    public ResponseEntity<TranslatorGetResponse> updateTranslator(
        Authentication authentication,
        @RequestBody TranslatorUpdateRequest request) {
        Long userId = userService.extractUserId(authentication);

        return ResponseEntity.ok(TranslatorGetResponse.mapToDto(translatorService.update(userId, request)));
    }

    @PostMapping("/withdrawal")
    @Operation(description = "Create withdrawal")
    public ResponseEntity<Withdrawal> createWithdrawal(
        Authentication authentication,
        @RequestBody TransactionCreateRequest request) {
        Long userId = userService.extractUserId(authentication);
        Withdrawal withdrawal = withdrawalService.createWithdrawal(userId, request);
        return ResponseEntity.ok(withdrawal);
    }

    //TODO подписка на статусы
    @PutMapping("/switch-status")
    @Operation(description = "Switch status")
    public ResponseEntity<Void> switchStatus(Authentication authentication) {
        Long userId = userService.extractUserId(authentication);
        translatorService.switchStatus(userId);
        return ResponseEntity.ok().build();
    }
}
