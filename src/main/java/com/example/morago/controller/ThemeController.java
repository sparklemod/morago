package com.example.morago.controller;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.ThemeResponse;
import com.example.morago.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public PageResponse<ThemeResponse> getThemes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Long categoryId,
            @Valid @ModelAttribute PageRequest pageRequest) {
        return themeService.getThemes(pageRequest, keyword, isActive, categoryId);
    }
}
