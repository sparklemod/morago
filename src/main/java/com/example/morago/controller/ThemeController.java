package com.example.morago.controller;

import com.example.morago.model.dto.PageRequest;
import com.example.morago.model.dto.PageResponse;
import com.example.morago.model.dto.ThemeResponse;
import com.example.morago.model.entity.Theme;
import com.example.morago.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<PageResponse<ThemeResponse>> getThemes(@RequestBody PageRequest pageRequest) {
        return ResponseEntity.ok(themeService.getThemes(pageRequest));
    }
}
