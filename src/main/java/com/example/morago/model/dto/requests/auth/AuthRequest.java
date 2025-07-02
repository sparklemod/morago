package com.example.morago.model.dto.requests.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String phone;
    private String password;
}
