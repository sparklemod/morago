package com.example.morago.model.entity;

import com.example.morago.model.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile user;
}
