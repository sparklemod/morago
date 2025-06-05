package com.example.morago.controller.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private Long id;
    private String phone;
    private String firstName;
    private String lastName;
}
