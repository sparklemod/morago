package com.example.morago.controller.dto.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Boolean isActive;
}
