package com.example.morago.controller.dto.requests.transaction;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class TransactionCreateRequest {
    private String accountHolder; // Имя + фамилия
    private String nameOfBank;    // Название банка
    private BigDecimal won;       // Сумма в вонах
}