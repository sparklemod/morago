package com.example.morago.service;

import com.example.morago.model.dto.requests.transaction.TransactionCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.DepositRepository;
import com.example.morago.repository.TranslatorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final TranslatorRepository translatorRepository;
    private final TranslatorService translatorService;

    @Transactional
    public Deposit createDeposit(Long userId, TransactionCreateRequest request) {
        Translator translator = translatorService.findById(userId);
        translator.setBalance(translator.getBalance().add(request.getWon()));
        translatorRepository.save(translator);

        Deposit deposit = Deposit.builder()
            .translator(translator)
            .won(request.getWon())
            .coin(request.getWon())
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.COMPLETED)
            .build();

        return depositRepository.save(deposit);
    }
}