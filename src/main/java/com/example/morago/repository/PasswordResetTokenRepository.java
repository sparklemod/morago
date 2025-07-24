package com.example.morago.repository;

import com.example.morago.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByPhoneAndOtpCodeAndUsedFalse(String phone, String otpCode);
    Optional<PasswordResetToken> findByResetTokenAndUsedFalse(String resetToken);
}
