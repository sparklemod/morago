package com.example.morago.model.dto.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @Schema(example = "+70000000002")
    private String phone;

    @Schema(example = "elena2@example.com")
    private String password;
}