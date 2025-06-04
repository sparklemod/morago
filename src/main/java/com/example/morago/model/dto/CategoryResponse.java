package com.example.morago.model.dto;

import com.example.morago.model.entity.Category;
import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private Boolean isActive;
}
