package com.example.morago.model.dto.response.theme;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserThemesResponse {
    private List<ThemeResponse> favoriteThemes;
    private List<ThemeResponse> defaultThemes;
}
