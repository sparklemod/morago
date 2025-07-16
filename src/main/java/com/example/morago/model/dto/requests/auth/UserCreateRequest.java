package com.example.morago.model.dto.requests.auth;

import com.example.morago.model.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "User create request")
public final class UserCreateRequest {
    @Schema(description = "User password", example = "password1")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 9,  message = "Password must be at least 9 characters")
    private String password;

    @Schema(description = "User password", example = "password1")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 9,  message = "Password must be at least 9 characters")
    private String confirmPassword;

    @Schema(description = "Phone number", example = "01012345678")
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only digits")
    @Size(min = 11,  message = "Phone must be at least 11 characters")
    private String phone;

    @Schema(description = "Role", example = "ROLE_TRANSLATOR")
    @NotNull(message = "Role cannot be empty")
    private RoleEnum role;
}