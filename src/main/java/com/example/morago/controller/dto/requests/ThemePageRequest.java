package com.example.morago.controller.dto.requests;

import lombok.Data;

@Data
public class ThemePageRequest extends PageRequest {
    private Boolean isActive;
    private Long categoryId;
    private String keyword;
}
