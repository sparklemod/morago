package com.example.morago.controller;

import com.example.morago.controller.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Deposit> createDeposit(@PathVariable Long userId,
        @RequestBody TransactionCreateRequest request) {
        Deposit deposit = depositService.createDeposit(userId, request);
        return ResponseEntity.ok(deposit);
    }
}