package com.example.morago.model.entity;

import com.example.morago.enums.PaymentStatusEnum;
import com.example.morago.model.entity.base.Auditable;
import com.example.morago.model.entity.base.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
