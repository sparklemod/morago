package com.example.morago.service;

import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.UserRepository;
import com.example.morago.repository.WithdrawalRepository;
import com.example.morago.util.CoinConverter;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
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

        BigDecimal coins = CoinConverter.convertWonToCoins(request.getWon());

        if (user.getBalance() < coins.longValue()) {
            throw new IllegalStateException("Insufficient funds on balance");
        }

        user.setBalance(user.getBalance() - coins.longValue());
        userRepository.save(user);

        Withdrawal withdrawal = Withdrawal.builder()
            .user(user)
            .sum(coins)
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.PENDING) //TODO отдельный метод для апрува?
            .build();

        return repository.save(withdrawal);
    }
}