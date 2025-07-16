package com.example.morago.controller;

import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.base.User;
import com.example.morago.service.CategoryService;
import com.example.morago.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ThemeController")
public class ThemeController {
    private final ThemeService themeService;
    private final CategoryService categoryService;

    @GetMapping("/themes")
    @Operation(description = "Get list of themes")
    @PreAuthorize("isAuthenticated() or permitAll()")
    public PageResponse<ThemeResponse> getThemes(
            @Valid @ModelAttribute ThemePageRequest themePageRequest,
            @AuthenticationPrincipal User user) {
        Long userId = user != null ? user.getId() : null;
        return themeService.getPublicThemes(themePageRequest, userId, themePageRequest.getCategoryId());
    }

    @GetMapping("/themes/{id}")
    @Operation(description = "Get theme by Id")
    public ThemeResponse getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }
}
