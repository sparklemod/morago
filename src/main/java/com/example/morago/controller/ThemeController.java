package com.example.morago.controller;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.ThemeResponse;
import com.example.morago.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
