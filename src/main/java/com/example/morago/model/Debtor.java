package com.example.morago.model;

import com.example.morago.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Debtor extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountHolder;
    private String nameOfBank;
    private Boolean isPaid;
    private Long userId;
}
