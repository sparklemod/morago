package com.example.morago.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolder;
    private String nameOfBank;
    private BigDecimal coin;
    private BigDecimal won;
    private String status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
