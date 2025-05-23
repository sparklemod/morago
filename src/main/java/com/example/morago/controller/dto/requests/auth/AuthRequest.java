package com.example.morago.controller.dto.requests.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String phone;
    private String password;
}
