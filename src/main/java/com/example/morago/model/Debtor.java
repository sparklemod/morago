package com.example.morago.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Debtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolder;
    private String nameOfBank;
    private Boolean isPaid;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
