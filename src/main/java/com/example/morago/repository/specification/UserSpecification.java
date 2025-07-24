package com.example.morago.repository.specification;

import com.example.morago.model.dto.requests.user.UserGetRequest;
import com.example.morago.model.entity.Deposit;
import com.example.morago.model.entity.UserProfile;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<UserProfile> build(UserGetRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String keyword = "%" + request.getKeyword().toLowerCase() + "%";

                Predicate keywordPredicate = cb.or(
                    cb.like(cb.lower(root.get("firstName")), keyword),
                    cb.like(cb.lower(root.get("lastName")), keyword),
                    cb.like(cb.lower(root.get("phone")), keyword),
                    cb.like(cb.lower(root.get("email")), keyword)
                );
                predicates.add(keywordPredicate);
            }

            if (request.getIsDebtor() != null) {
                predicates.add(cb.equal(root.get("isDebtor"), request.getIsDebtor()));
            }

            if (request.getHasDeposit() != null) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Deposit> subRoot = subquery.from(Deposit.class);
                subquery.select(cb.literal(1L));
                Predicate userMatch = cb.equal(subRoot.get("user"), root);
                subquery.where(userMatch);

                if (request.getHasDeposit()) {
                    predicates.add(cb.exists(subquery));
                } else {
                    predicates.add(cb.not(cb.exists(subquery)));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

