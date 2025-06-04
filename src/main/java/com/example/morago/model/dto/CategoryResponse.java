package com.example.morago.model.dto;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Boolean isActive;
}
