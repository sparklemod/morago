package com.example.morago.controller;

import com.example.morago.controller.dto.requests.deposit.DepositCreateRequest;
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
        @RequestBody DepositCreateRequest request) {
        Deposit deposit = depositService.createDeposit(userId, request);
        return ResponseEntity.ok(deposit);
    }
}