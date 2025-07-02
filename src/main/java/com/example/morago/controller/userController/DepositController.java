package com.example.morago.controller.userController;

import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/{userId}")
    public ResponseEntity<Deposit> createDeposit(@PathVariable Long userId,
        @RequestBody TransactionCreateRequest request) {
        Deposit deposit = depositService.createDeposit(userId, request);
        return ResponseEntity.ok(deposit);
    }
}