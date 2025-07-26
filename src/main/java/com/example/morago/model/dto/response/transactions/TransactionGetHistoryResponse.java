package com.example.morago.model.dto.response.transactions;

import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.enums.PaymentStatusEnum;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record TransactionGetHistoryResponse(
    Long id,
    String date,
    BigDecimal amount,
    PaymentStatusEnum status
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    public static TransactionGetHistoryResponse mapDepositToDto(Deposit deposit) {
        return TransactionGetHistoryResponse.builder()
            .id(deposit.getId())
            .date(deposit.getCreatedAt().format(FORMATTER))
            .amount(deposit.getWon())
            .status(deposit.getStatus())
            .build();
    }

    public static TransactionGetHistoryResponse mapWithdrawalToDto(Withdrawal withdrawal) {
        return TransactionGetHistoryResponse.builder()
            .date(withdrawal.getCreatedAt().format(FORMATTER))
            .amount(withdrawal.getSum())
            .status(withdrawal.getStatus())
            .build();
    }
}
