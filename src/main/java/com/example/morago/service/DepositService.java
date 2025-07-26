package com.example.morago.service;

import com.example.morago.model.dto.response.transactions.TransactionGetHistoryResponse;
import com.example.morago.model.dto.requests.transactions.deposit.DepositApproveRequest;
import com.example.morago.model.dto.requests.transactions.TransactionCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.base.User;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.DepositRepository;
import com.example.morago.repository.UserRepository;
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
public class DepositService {

    private final DepositRepository repository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    public Deposit getLastDepositByUser(Long userId) {
        return repository.findFirstByUserIdAndStatusOrderByCreatedAtDesc(
            userId,
            PaymentStatusEnum.PENDING
        ).orElseThrow(() -> new HandledException("Deposit not found, userId: " + userId));
    }

    public Page<TransactionGetHistoryResponse> getHistory(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable)
            .map(TransactionGetHistoryResponse::mapDepositToDto);
    }

    @Transactional
    public void approveDeposit(Long id, DepositApproveRequest req) {
        Deposit deposit = repository.findDepositById(id)
            .orElseThrow(() -> new HandledException("Deposit not found, id: " + id));
        User user = deposit.getUser();

        validateBankDetails(user, req);

        deposit.setWon(req.sum());
        deposit.setCoin(req.sum());
        deposit.setStatus(PaymentStatusEnum.COMPLETED);
        repository.save(deposit);

        user.setBalance(
            user.getBalance().add(req.sum())
        );
        userRepository.save(user);

        NotificationDto dto = new NotificationDto(
            "Deposit approved",
            String.format("Sum: %d", deposit.getWon().intValue())
        );

        notificationService.createAndSendNotificationToUser(dto, user);
    }

    @Transactional
    public Deposit createDeposit(Long userId, TransactionCreateRequest request) {
        User user = userService.getUserById(userId);

        Deposit deposit = Deposit.builder()
            .user(user)
            .won(request.getWon())
            .coin(request.getWon())
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.PENDING)
            .build();
        repository.save(deposit);

        NotificationDto dto = new NotificationDto(
            String.format("New Deposit [%s]", user.getNameWithSurname()),
            String.format("Sum: %d, user id: %d", deposit.getWon().intValue(), user.getId())
        );

        notificationService.createAndSendNotificationToAdmins(dto);

        return deposit;
    }

    private void validateBankDetails(User user, DepositApproveRequest req) {
        if (!user.getNameWithSurname().equals(req.fullName())) {
            throw new HandledException("Name does not match the deposit owner");
        }

        if (!user.getPhone().equals(req.phone())) {
            throw new HandledException("Phone does not match the deposit owner");
        }
    }
}