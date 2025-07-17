package com.example.morago.model.dto.response.transactions;

import com.example.morago.model.enums.PaymentStatusEnum;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record TransactionGetHistoryResponse(
    String date,
    BigDecimal amount,
    PaymentStatusEnum status
) {}
