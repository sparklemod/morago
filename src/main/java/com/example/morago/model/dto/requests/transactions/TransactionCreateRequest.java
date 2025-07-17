package com.example.morago.model.dto.requests.transactions;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class TransactionCreateRequest {
    private String accountHolder;
    private String nameOfBank;
    private BigDecimal won;
}