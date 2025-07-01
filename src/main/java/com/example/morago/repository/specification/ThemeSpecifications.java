package com.example.morago.repository.specification;

import com.example.morago.model.entity.Call;
import com.example.morago.model.entity.Theme;
import com.example.morago.model.entity.Translator;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ThemeSpecifications {
    // Для админа
    public static Specification<Theme> isActive(Boolean isActive) {
        return (root, query, builder) -> isActive == null ? builder.conjunction()
                : builder.equal(root.get("isActive"), isActive);
    }

    public static Specification<Theme> hasCategory(Long categoryId) {
        return (root, query, builder) -> categoryId == null ? builder.conjunction()
                : builder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Theme> nameContains(String keyword) {
        return (root, query, builder) -> keyword == null ? builder.conjunction()
                : builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }

    // Для не авторизованного (isPopular + id)
    public static Specification<Theme> withPopular() {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("isPopular")), builder.asc(root.get("id")));
            return builder.conjunction();
        };
    }

    // Сортировка для авторизованного (по последнему звонку)
    public static Specification<Theme> withLastCall(Long userId) {
        return (root, query, criteriaBuilder) -> {
            Join<Theme, Translator> translatorJoin = root.join("translators", JoinType.LEFT);
            Join<Translator, Call> callJoin = translatorJoin.join("calls", JoinType.LEFT);
            Predicate filter = userId == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(callJoin.get("user").get("id"), userId);
            query.orderBy(criteriaBuilder.desc(callJoin.get("createdAt")));
            query.distinct(true);
            return filter;
        };
    }

    // Комбинирование фильтров (админ)
    public static Specification<Theme> combineForAdmin(String keyword, Boolean isActive, Long categoryId) {
        return Specification.where(nameContains(keyword))
                .and(isActive(isActive))
                .and(hasCategory(categoryId));
    }

    // Авторизован
    public static Specification<Theme> forAuthenticated(String keyword, Long userId) {
        return Specification.where(nameContains(keyword))
                .and(withLastCall(userId));
    }

    // Не авторизован
    public static Specification<Theme> forAnonymous() {
        return Specification.where(withPopular());
    }
}
