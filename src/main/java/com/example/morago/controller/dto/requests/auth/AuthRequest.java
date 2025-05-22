package com.example.morago.controller.dto.requests.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private Integer phone;
    private String password;
}
