package com.example.morago.controller.adminController;

import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.category.CategoryRequest;
import com.example.morago.model.entity.Category;
import com.example.morago.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping
    public Page<Category> getCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getAdminCategories(categoryPageRequest);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryByIdOrThrow(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Category deleteCategory(@PathVariable Long id) {
        return categoryService.softDeleteCategoryById(id);
    }
}
