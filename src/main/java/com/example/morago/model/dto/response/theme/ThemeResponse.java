package com.example.morago.model.dto.response.theme;

import lombok.Data;

@Data
public class ThemeResponse {
    private Long id;
    private String name;
    private Boolean isActive;
    private Long iconId;
    private Long categoryId;
    private Boolean isPopular;
}
