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
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String accountHolder;
    private String nameOfBank;
    private BigDecimal sum;
    private String status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
