package com.example.morago.model.dto.requests.transactions.withdrawal;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record WithdrawalApproveRequest(
    @Schema(description = "Name, Surname", example = "Dmitrii Kim")
    String fullName,

    @Schema(description = "Bank name", example = "Hana Bank")
    String bankName,

    @Schema(description = "Bank account", example = "4400 9999 2313 23 05")
    String bankAccount,

    @Schema(description = "Sum", example = "20000")
    BigDecimal sum
) {}
