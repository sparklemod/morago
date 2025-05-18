package com.example.morago.model.Dto;

import lombok.Data;

@Data
public class AuthRequest {
    private Integer phone;
    private String password;
}
