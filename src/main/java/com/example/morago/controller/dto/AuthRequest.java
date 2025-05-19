package com.example.morago.controller.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private Integer phone;
    private String password;
}
