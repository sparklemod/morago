package com.example.morago.controller.dto.response;

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
