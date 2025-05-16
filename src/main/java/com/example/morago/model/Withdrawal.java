package com.example.morago.model;

import com.example.morago.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
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
    private String status;
    private Long userId;
}
