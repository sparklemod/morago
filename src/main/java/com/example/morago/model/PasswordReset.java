package com.example.morago.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private String phone;
    private String token;
    private Integer resetCode;
}
