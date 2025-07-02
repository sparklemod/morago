package com.example.morago.controller;

import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Page<Category> getCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getPublicCategories(categoryPageRequest);
    }

    @GetMapping("/{id}/themes")
    public PageResponse<ThemeResponse> getThemesByCategory(
            @PathVariable Long id,
            @ModelAttribute ThemePageRequest themePageRequest,
            Authentication auth) {
        // Проверка авторизации
        Long userId = (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String))
                ? ((User) auth.getPrincipal()).getId()
                : null;
        return categoryService.getThemesByCategoryId(id, themePageRequest, userId);
    }
}
