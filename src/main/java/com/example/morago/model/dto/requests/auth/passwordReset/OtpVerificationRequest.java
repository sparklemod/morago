package com.example.morago.model.dto.requests.auth.passwordReset;

import lombok.Data;

@Data
public class OtpVerificationRequest {
    private String phone;
    private String code;
}
