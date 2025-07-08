package com.example.morago.service;

import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.UserRepository;
import com.example.morago.repository.WithdrawalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final WithdrawalRepository repository;
    private final UserRepository userRepository;
    private final UserProfileService userService;

    @Transactional
    public Withdrawal createWithdrawal(Long userId, TransactionCreateRequest request) {
        User user = userService.findById(userId);

        if (user.getBalance().compareTo(request.getWon()) < 0) {
            throw new IllegalStateException("Insufficient funds on balance");
        }

        user.setBalance(user.getBalance().subtract(request.getWon()));
        userRepository.save(user);

        Withdrawal withdrawal = Withdrawal.builder()
            .user(user)
            .sum(request.getWon())
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.PENDING)
            .build();

        return repository.save(withdrawal);
    }
}