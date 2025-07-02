package com.example.morago.model.dto.requests.theme;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ThemeRequest {
    @NotBlank(message = "Name can't be empty")
    private String name;

    private Boolean isActive;

    @NotNull
    private Long categoryId;

    private Long iconId;

    private Boolean isPopular;
}
