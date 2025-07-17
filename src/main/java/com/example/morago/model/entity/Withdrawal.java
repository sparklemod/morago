package com.example.morago.model.entity;

import com.example.morago.model.enums.PaymentStatusEnum;
import com.example.morago.model.entity.base.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Withdrawal extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String accountHolder;
    private String nameOfBank;
    private BigDecimal sum;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Translator translator;
}
