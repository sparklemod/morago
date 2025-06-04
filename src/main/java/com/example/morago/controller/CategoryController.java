package com.example.morago.controller;

import com.example.morago.model.dto.CategoryResponse;
import com.example.morago.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }
}
