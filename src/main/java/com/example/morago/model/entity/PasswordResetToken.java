package com.example.morago.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Телефон, на который отправили код */
    private String phone;

    /** 4‑6‑значный код, который вводит пользователь */
    private String otpCode;

    /** Временный UUID‑токен, который мы выдаём ПОСЛЕ верификации кода */
    private String resetToken;

    /** Дата истечения кода (обычно +3‑10 мин) */
    private LocalDateTime expirationAt;

    /** Использован ли уже код/токен */
    private boolean used = false;
}
