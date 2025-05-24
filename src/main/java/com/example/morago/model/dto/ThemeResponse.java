package com.example.morago.model.dto;

import com.example.morago.model.entity.Theme;
import lombok.Data;

@Data
public class ThemeResponse {
    private Long id;
    private String name;
    private Boolean isActive;
    private String iconName;
    private String categoryName;

    public ThemeResponse(Theme theme) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.isActive = theme.getIsActive();
        this.iconName = theme.getIcon() != null ? theme.getIcon().getOriginalTitle() : null;
        this.categoryName = theme.getCategory() != null ? theme.getCategory().getName() : null;
    }
}
