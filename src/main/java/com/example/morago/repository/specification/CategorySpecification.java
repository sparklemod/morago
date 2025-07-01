package com.example.morago.repository.specification;

import com.example.morago.model.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    // Фильтр по isActive
    public static Specification<Category> isActive(Boolean isActive) {
        return (root, query, builder) -> isActive == null ? builder.conjunction()
                : builder.equal(root.get("isActive"), isActive);
    }
    // Фильтр по имени
    public static Specification<Category> nameContains(String keyword) {
        return (root, query, builder) -> keyword == null ? builder.conjunction()
                : builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }

    // Комбинация фильтров для Админа
    public static Specification<Category> combineForAdmin(String keyword, Boolean isActive) {
        return Specification.where(nameContains(keyword)).and(isActive(isActive));
    }

    // для Авторизованных
    public static Specification<Category> forAuthenticated() {
        return (root, query, builder) -> builder.equal(root.get("isActive"), true);
    }
}
