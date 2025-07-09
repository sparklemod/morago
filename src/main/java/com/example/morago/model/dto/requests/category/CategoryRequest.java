package com.example.morago.model.dto.requests.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Name can't be empty")
    private String name;

    private Boolean isActive;
}
