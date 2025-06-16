package com.example.morago.controller.dto.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "User create request")
public final class UserCreateRequest {
    @Schema(description = "User password", example = "password")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8,  message = "Password must be at least 8 characters")
    private String password;

    @Schema(description = "Phone number", example = "+82000000008")
    @NotBlank(message = "Phone cannot be empty")
    private String phone;
}