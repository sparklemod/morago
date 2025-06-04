package com.example.morago.service;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.controller.dto.response.ThemeResponse;
import com.example.morago.model.entity.Theme;
import com.example.morago.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public PageResponse<ThemeResponse> getThemes(PageRequest pageRequest) {
        String[] sortParts = pageRequest.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(sortParts[1]), sortParts[0]);
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getPageSize(), sort);

        Page<Theme> themePage = themeRepository.findAll(pageable);

        Page<ThemeResponse> responsePage = themePage.map(theme -> {
            ThemeResponse response = new ThemeResponse();
            response.setId(theme.getId());
            response.setName(theme.getName());
            response.setIsActive(theme.getIsActive());
            response.setIconName(theme.getIcon().getOriginalTitle());
            response.setCategoryName(theme.getCategory().getName());
            response.setIsPopular(theme.getIsPopular());
            return response;
        });

        return new PageResponse<>(responsePage);
    }
}
