package com.example.morago.model.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Translator create request")
public abstract class UserUpdateRequest {

    @Schema(description = "User id", example = "3")
    @NotBlank(message = "Can not be empty")
    protected Long id;

    @Schema(description = "Translator firstname", example = "Svetlana")
    @NotBlank(message = "Can not be empty")
    @Size(max = 50, message = "Must be less than 50 characters")
    protected String firstName;

    @Schema(description = "Translator lastname", example = "Prokhorova")
    @NotBlank(message = "Can not be empty")
    @Size(max = 50, message = "Must be less than 50 characters")
    protected String lastName;

    @Schema(description = "Image URL")
    protected String imageUrl;
}