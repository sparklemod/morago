package com.example.morago.controller;

import com.example.morago.controller.dto.requests.deposit.DepositCreateRequest;
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
@RequestMapping("/api/withdrawal")
@RequiredArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Withdrawal> createDeposit(@PathVariable Long userId,
        @RequestBody DepositCreateRequest request) {
        Withdrawal deposit = withdrawalService.createWithdrawal(userId, request);
        return ResponseEntity.ok(deposit);
    }
}