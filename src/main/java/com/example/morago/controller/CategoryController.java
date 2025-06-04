package com.example.morago.controller;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.response.CategoryResponse;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<PageResponse<CategoryResponse>> getCategories(@RequestBody PageRequest pageRequest) {
        return ResponseEntity.ok(categoryService.getCategories(pageRequest));
    }
}
