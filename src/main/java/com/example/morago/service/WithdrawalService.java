package com.example.morago.service;

import com.example.morago.model.dto.requests.transactions.TransactionCreateRequest;
import com.example.morago.model.dto.requests.transactions.TransactionGetHistoryResponse;
import com.example.morago.model.dto.requests.transactions.withdrawal.WithdrawalApproveRequest;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.UserRepository;
import com.example.morago.repository.WithdrawalRepository;
import com.example.morago.util.exception.HandledException;
import jakarta.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final WithdrawalRepository repository;
    private final UserRepository userRepository;
    private final TranslatorService translatorService;

    public Withdrawal getLastWithdrawalByUser(Long userId) {
        return repository.findFirstByTranslatorIdAndStatusOrderByCreatedAtDesc(
            userId,
            PaymentStatusEnum.PENDING
        ).orElseThrow(() -> new HandledException("Withdrawal not found, userId: " + userId));
    }

    public Page<TransactionGetHistoryResponse> getHistory(Long userId, Pageable pageable) {
        return repository.findByTranslatorId(userId, pageable)
            .map(this::mapWithdrawalToDto);
    }

    @Transactional
    public void approveWithdrawal(Long id, WithdrawalApproveRequest req) {
        Withdrawal withdrawal = repository.findWithdrawalById(id)
            .orElseThrow(() -> new HandledException("Withdrawal not found, id: " + id));

        Translator user = withdrawal.getTranslator();

        validateBankDetails(user, req);

        withdrawal.setSum(req.sum());
        withdrawal.setStatus(PaymentStatusEnum.COMPLETED);
        repository.save(withdrawal);

        user.setBalance(
            user.getBalance().subtract(req.sum())
        );
        userRepository.save(user);
    }

    @Transactional
    public Withdrawal createWithdrawal(Long userId, TransactionCreateRequest request) {
        Translator translator = translatorService.findById(userId);

        Withdrawal withdrawal = Withdrawal.builder()
            .translator(translator)
            .sum(request.getWon())
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.PENDING)
            .build();

        return repository.save(withdrawal);
    }

    private TransactionGetHistoryResponse mapWithdrawalToDto(Withdrawal withdrawal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        return TransactionGetHistoryResponse.builder()
            .date(withdrawal.getCreatedAt().format(formatter))
            .amount(withdrawal.getSum())
            .status(withdrawal.getStatus())
            .build();
    }

    private void validateBankDetails(Translator user, WithdrawalApproveRequest req) {
        if (!user.getFullName().equals(req.fullName())) {
            throw new HandledException("Name does not match the withdrawal owner");
        }

        if (user.getBalance().compareTo(req.sum()) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
    }
}