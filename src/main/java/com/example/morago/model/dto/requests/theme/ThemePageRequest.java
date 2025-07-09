package com.example.morago.model.dto.requests.theme;

import com.example.morago.model.dto.requests.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ThemePageRequest extends PageRequest {
    private Boolean isActive;
    @Schema(hidden = true)
    private Long categoryId;
    private String keyword;
}
