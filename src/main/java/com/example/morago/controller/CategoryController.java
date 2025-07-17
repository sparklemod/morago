package com.example.morago.controller;

import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/categories")
@Tag(name = "CategoryController")
public class CategoryController {
    private final CategoryService categoryService;

    //TODO Саша посмотри контроллер
    @GetMapping()
    @Operation(description = "Get public list of categories")
    public Page<Category> getPublicCategories(@ModelAttribute CategoryPageRequest categoryPageRequest) {
        return categoryService.getPublicCategories(categoryPageRequest);
    }

    @GetMapping("/{id}/themes")
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
}
