package com.example.morago.service;

import com.example.morago.model.dto.requests.transactions.TransactionCreateRequest;
import com.example.morago.model.dto.response.transactions.TransactionGetHistoryResponse;
import com.example.morago.model.dto.requests.transactions.withdrawal.WithdrawalApproveRequest;
import com.example.morago.model.entity.Translator;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.UserRepository;
import com.example.morago.repository.WithdrawalRepository;
import com.example.morago.service.notification.NotificationService;
import com.example.morago.service.notification.dto.NotificationDto;
import com.example.morago.util.exception.HandledException;
import jakarta.transaction.Transactional;
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
    private final NotificationService notificationService;

    public Withdrawal getLastWithdrawalByUser(Long userId) {
        return repository.findFirstByTranslatorIdAndStatusOrderByCreatedAtDesc(
            userId,
            PaymentStatusEnum.PENDING
        ).orElseThrow(() -> new HandledException("Withdrawal not found, userId: " + userId));
    }

    public Page<TransactionGetHistoryResponse> getHistory(Long userId, Pageable pageable) {
        return repository.findByTranslatorId(userId, pageable)
            .map(TransactionGetHistoryResponse::mapWithdrawalToDto);
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

        NotificationDto dto = new NotificationDto(
            "Withdrawal approved",
            String.format("Sum: %d", withdrawal.getSum().intValue())
        );

        notificationService.createAndSendNotificationToUser(dto, user);
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
        repository.save(withdrawal);

        NotificationDto dto = new NotificationDto(
            String.format("New Withdrawal [%s]", translator.getNameWithSurname()),
            String.format("Sum: %d, user id: %d", withdrawal.getSum().intValue(), translator.getId())
        );

        notificationService.createAndSendNotificationToAdmins(dto);

        return withdrawal;
    }

    private void validateBankDetails(Translator user, WithdrawalApproveRequest req) {
        if (!user.getNameWithSurname().equals(req.fullName())) {
            throw new HandledException("Name does not match the withdrawal owner");
        }

        if (user.getBalance().compareTo(req.sum()) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
    }
}