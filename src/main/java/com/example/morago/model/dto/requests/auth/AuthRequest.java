package com.example.morago.model.dto.requests.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String phone;
    private String password;
}