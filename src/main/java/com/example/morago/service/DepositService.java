package com.example.morago.service;

import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.Translator;
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
    private final TranslatorService translatorService;

    @Transactional
    public Deposit createDeposit(Long userId, TransactionCreateRequest request) {
        Translator translator = translatorService.findById(userId);

        BigDecimal coins = CoinConverter.convertWonToCoins(request.getWon());
        translator.setBalance(translator.getBalance() + coins.longValue());
        userRepository.save(translator);

        Deposit deposit = Deposit.builder()
            .translator(translator)
            .won(request.getWon())
            .coin(coins)
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.COMPLETED)
            .build();

        return depositRepository.save(deposit);
    }
}