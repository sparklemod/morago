package com.example.morago.controller.translatorController;

import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.service.WithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translator/withdrawal")
@RequiredArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @PostMapping("/{userId}")
    public ResponseEntity<Withdrawal> createDeposit(@PathVariable Long userId,
        @RequestBody TransactionCreateRequest request) {
        Withdrawal deposit = withdrawalService.createWithdrawal(userId, request);
        return ResponseEntity.ok(deposit);
    }
}