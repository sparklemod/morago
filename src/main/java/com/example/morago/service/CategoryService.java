package com.example.morago.service;

import com.example.morago.model.dto.requests.category.CategoryPageRequest;
import com.example.morago.model.dto.requests.category.CategoryRequest;
import com.example.morago.model.dto.requests.theme.ThemePageRequest;
import com.example.morago.model.dto.response.PageResponse;
import com.example.morago.model.dto.response.theme.ThemeResponse;
import com.example.morago.model.entity.Category;
import com.example.morago.repository.CategoryRepository;
import com.example.morago.repository.specification.CategorySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ThemeService themeService;

    // Создание
    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        mapAndValidate(category, categoryRequest);
        return categoryRepository.save(category);
    }

    // Редакрирование
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category existing = getCategoryByIdOrThrow(id);
        mapAndValidate(existing, categoryRequest);
        return categoryRepository.save(existing);
    }

    // Мягкое удаление, для сохранения в истории и чтоб при случайном удалении не удалились темы
    public Category softDeleteCategoryById(Long id) {
        Category category = getCategoryByIdOrThrow(id);
        category.setIsActive(false);
        categoryRepository.save(category);
        return category;
    }

    // Публичный список категорий
    public Page<Category> getPublicCategories(CategoryPageRequest categoryPageRequest) {
        Specification<Category> spec = CategorySpecification.forAuthenticated();
        Pageable pageable = categoryPageRequest.toPageableWithoutSort();
        return categoryRepository.findAll(spec, pageable);
    }

    // Список категорий для Админа
    public Page<Category> getAdminCategories(CategoryPageRequest categoryPageRequest) {
        Specification<Category> spec = CategorySpecification.combineForAdmin(categoryPageRequest.getKeyword(),
                categoryPageRequest.getIsActive());
        Pageable pageable = categoryPageRequest.toPageableWithoutSort();
        return categoryRepository.findAll(spec, pageable);
    }

    // Получение категории по id
    public Category getCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    // Получение тем по категории
    public PageResponse<ThemeResponse> getThemesByCategoryId(
            Long categoryId,
            ThemePageRequest themePageRequest,
            Long userId) {
        getCategoryByIdOrThrow(categoryId);
        return themeService.getPublicThemes(themePageRequest, userId, categoryId);
    }

    // Маппинг и валидация
    private void mapAndValidate(Category category, CategoryRequest request) {
        category.setName(request.getName());
        category.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (category.getName().length() > 100) {
            throw new IllegalArgumentException("Name must be less than 100 characters");
        }
    }
}
