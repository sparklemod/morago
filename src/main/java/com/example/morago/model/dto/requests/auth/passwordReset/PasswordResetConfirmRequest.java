package com.example.morago.model.dto.requests.auth.passwordReset;

import lombok.Data;

@Data
public class PasswordResetConfirmRequest {
    private String resetToken;
    private String newPassword;
}
