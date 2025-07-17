package com.example.morago.service;

import com.example.morago.model.dto.response.transactions.TransactionGetHistoryResponse;
import com.example.morago.model.dto.requests.transactions.deposit.DepositApproveRequest;
import com.example.morago.model.dto.requests.transactions.TransactionCreateRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.UserProfile;
import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.repository.DepositRepository;
import com.example.morago.repository.UserRepository;
import com.example.morago.util.exception.HandledException;
import jakarta.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository repository;
    private final UserRepository userRepository;
    private final UserProfileService userService;

    public Deposit getLastDepositByUser(Long userId) {
        return repository.findFirstByUserIdAndStatusOrderByCreatedAtDesc(
            userId,
            PaymentStatusEnum.PENDING
        ).orElseThrow(() -> new HandledException("Deposit not found, userId: " + userId));
    }

    public Page<TransactionGetHistoryResponse> getHistory(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable)
            .map(this::mapDepositToDto);
    }

    @Transactional
    public void approveDeposit(Long id, DepositApproveRequest req) {
        Deposit deposit = repository.findDepositById(id)
            .orElseThrow(() -> new HandledException("Deposit not found, id: " + id));
        UserProfile user = deposit.getUser();

        validateBankDetails(user, req);

        deposit.setWon(req.sum());
        deposit.setCoin(req.sum());
        deposit.setStatus(PaymentStatusEnum.COMPLETED);
        repository.save(deposit);


        user.setBalance(
            user.getBalance().add(req.sum())
        );
        userRepository.save(user);
    }

    @Transactional
    public Deposit createDeposit(Long userId, TransactionCreateRequest request) {
        UserProfile user = userService.findById(userId);

        Deposit deposit = Deposit.builder()
            .user(user)
            .won(request.getWon())
            .coin(request.getWon())
            .accountHolder(request.getAccountHolder())
            .nameOfBank(request.getNameOfBank())
            .status(PaymentStatusEnum.PENDING)
            .build();

        return repository.save(deposit);
    }

    private TransactionGetHistoryResponse mapDepositToDto(Deposit deposit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

        return TransactionGetHistoryResponse.builder()
            .date(deposit.getCreatedAt().format(formatter))
            .amount(deposit.getWon())
            .status(deposit.getStatus())
            .build();
    }

    private void validateBankDetails(UserProfile user, DepositApproveRequest req) {
        if (!user.getFullName().equals(req.fullName())) {
            throw new HandledException("Name does not match the deposit owner");
        }

        if (!user.getPhone().equals(req.phone())) {
            throw new HandledException("Phone does not match the deposit owner");
        }
    }
}