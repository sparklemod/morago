package com.example.morago.service;

import com.example.morago.controller.dto.requests.PageRequest;
import com.example.morago.controller.dto.response.CategoryResponse;
import com.example.morago.controller.dto.response.PageResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public PageResponse<CategoryResponse> getCategories(PageRequest pageRequest) {
        String[] sortParts = pageRequest.getSort().split(",");
        Sort sort = Sort.by(Sort.Direction.fromString(sortParts[1]), sortParts[0]);
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getPageSize(), sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        Page<CategoryResponse> responsePage = categoryPage.map(category -> {
            CategoryResponse response = new CategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            response.setIsActive(category.getIsActive());
            return response;
        });

        return new PageResponse<>(responsePage);
    }
}
