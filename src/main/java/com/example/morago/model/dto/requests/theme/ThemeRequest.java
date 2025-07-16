package com.example.morago.model.dto.requests.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ThemeRequest {
    @Schema(description = "Name of the theme", example = "Medicine", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Schema(description = "Optional title for the theme", example = "Медицина")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Schema(description = "Detailed description of the theme")
    private String description;

    @Schema(description = "Standard daytime price for the theme", example = "15000")
    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;

    @Schema(description = "Nighttime price for the theme", example = "18000")
    @Min(value = 0, message = "Night price cannot be negative")
    private BigDecimal nightPrice;

    @Schema(description = "Flag the theme is popular", example = "false", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "false")
    @NotNull(message = "Popular flag must be specified")
    private Boolean isPopular = false;

    @Schema(description = "Flag the theme is active", example = "true", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "true")
    @NotNull(message = "Active flag must be specified")
    private Boolean isActive = true;

    @Schema(description = "ID of the icon file linked to the theme (optional)", example = "42")
    private Long iconId;

    @Schema(description = "ID of the category this theme belongs to", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Category ID must be provided")
    private Long categoryId;
}
