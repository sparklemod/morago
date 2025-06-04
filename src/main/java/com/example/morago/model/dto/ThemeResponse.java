package com.example.morago.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ThemeResponse {
    private Long id;
    private String name;
    private Boolean isActive;
    private String iconName;
    private String categoryName;
    private Boolean isPopular;
}
