package com.example.morago.controller.dto.requests.theme;

import com.example.morago.controller.dto.requests.PageRequest;
import lombok.Data;

@Data
public class ThemePageRequest extends PageRequest {
    private Boolean isActive;
    private Long categoryId;
    private String keyword;
}
