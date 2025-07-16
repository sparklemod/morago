package com.example.morago.service;

import com.example.morago.model.dto.requests.auth.passwordReset.OtpVerificationRequest;
import com.example.morago.model.dto.requests.auth.passwordReset.PasswordResetConfirmRequest;
import com.example.morago.model.dto.requests.auth.passwordReset.PasswordResetRequest;
import com.example.morago.model.entity.PasswordResetToken;
import com.example.morago.model.entity.base.User;
import com.example.morago.repository.PasswordResetTokenRepository;
import com.example.morago.repository.UserRepository;
import com.example.morago.service.notification.sms.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;
    private final UserService userService;

    // «Сбросить пароль» — выдаём код
    public void requestReset(PasswordResetRequest request) {
        userService.getUserByPhone(request.getPhone());

        int rawCode = 1000 + ThreadLocalRandom.current().nextInt(9000);
        String otpCode = String.valueOf(rawCode);

        PasswordResetToken token = new PasswordResetToken();
        token.setPhone(request.getPhone());
        token.setOtpCode(otpCode);
        token.setExpirationAt(LocalDateTime.now().plusMinutes(3));
        tokenRepository.save(token);

        smsService.send(request.getPhone(), "Код для сброса пароля: " + otpCode);

        log.debug("OTP {} выдан для {}", otpCode, request.getPhone());
    }

    //«Подтвердить код» — отдаём resetToken
    public String verifyCode(OtpVerificationRequest request) {
        PasswordResetToken token = tokenRepository
                .findByPhoneAndOtpCodeAndUsedFalse(request.getPhone(), request.getCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP code"));
        if (token.getExpirationAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Otp code has expired");
        }

        String resetToken = UUID.randomUUID().toString();
        token.setResetToken(resetToken);
        tokenRepository.save(token);
        return resetToken;
    }
    // Шаг «Новый пароль» — меняем пароль
    public void confirmPasswordReset(PasswordResetConfirmRequest request) {
        PasswordResetToken token = tokenRepository
                .findByResetTokenAndUsedFalse(request.getResetToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid/Used token"));

        if (token.getExpirationAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token has expired");
        }

        User user = userService.getUserByPhone(token.getPhone());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        token.setUsed(true);
        tokenRepository.save(token);
        userRepository.save(user);

        log.info("Пароль сброшен для {}", user.getPhone());
    }
}
