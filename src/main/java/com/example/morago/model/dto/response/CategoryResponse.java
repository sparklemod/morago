package com.example.morago.model.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Boolean isActive;
}
