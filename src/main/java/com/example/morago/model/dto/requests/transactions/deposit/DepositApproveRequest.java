package com.example.morago.model.dto.requests.transactions.deposit;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record DepositApproveRequest(
    @Schema(description = "Name, Surname", example = "Dmitrii Kim")
    String fullName,

    @Schema(description = "phone", example = "01012345678")
    String phone,

    @Schema(description = "Sum", example = "20000")
    BigDecimal sum
) {}
