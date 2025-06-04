package com.example.morago.service;

import com.example.morago.model.dto.CategoryResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Page<CategoryResponse> getAllCategories(final Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryResponse::new);
    }
}
