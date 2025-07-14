package com.example.morago.controller;

import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.category.CategoryRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    @Operation(description = "Get public list of categories")
    public Page<Category> getPublicCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getPublicCategories(categoryPageRequest);
    }

    @GetMapping("/category/{id}/themes")
    @Operation(description = "Get public themes by category")
    public PageResponse<ThemeResponse> getPublicThemesByCategory(
            @PathVariable Long id,
            @ModelAttribute ThemePageRequest themePageRequest,
            Authentication auth) {

        Long userId = (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String))
                ? ((User) auth.getPrincipal()).getId()
                : null;
        return categoryService.getThemesByCategoryId(id, themePageRequest, userId);
    }

    //ADMIN
    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/admin/categories")
    @Operation(description = "Get list of categories (admin)")
    public Page<Category> getCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getAdminCategories(categoryPageRequest);
    }

    @GetMapping("/admin/categories/{id}")
    @Operation(description = "Get category by Id (admin)")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryByIdOrThrow(id);
    }

    @PutMapping("/admin/categories/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @DeleteMapping("/admin/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Category deleteCategory(@PathVariable Long id) {
        return categoryService.softDeleteCategoryById(id);
    }
}
