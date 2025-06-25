package com.example.morago.service;

import com.example.morago.controller.dto.requests.deposit.DepositCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.DepositRepository;
import com.example.morago.repository.UserRepository;
import com.example.morago.util.CoinConverter;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final UserProfileService userService;

    @Transactional
    public Deposit createDeposit(Long userId, DepositCreateRequest request) {
        User user = userService.findById(userId);

        BigDecimal coins = CoinConverter.convertWonToCoins(request.getWon());
        user.setBalance(user.getBalance() + coins.longValue());
        userRepository.save(user);

        Deposit deposit = Deposit.builder()
            .user(user)
            .won(request.getWon())
            .coin(coins)
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.COMPLETED)
            .build();

        return depositRepository.save(deposit);
    }
}