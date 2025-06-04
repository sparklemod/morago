package com.example.morago.specification;

import com.example.morago.controller.dto.requests.user.UserGetRequest;
import com.example.morago.model.entity.UserProfile;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserProfileSpecification {

    public static Specification<UserProfile> build(UserGetRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getFirstName() != null) {
                predicates.add(cb.like(
                    cb.lower(root.get("firstName")),
                    "%" + request.getFirstName().toLowerCase() + "%"));
            }

            if (request.getLastName() != null) {
                predicates.add(cb.like(
                    cb.lower(root.get("lastName")),
                    "%" + request.getLastName().toLowerCase() + "%"));
            }

            if (request.getPhone() != null) {
                predicates.add(cb.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }

            if (request.getEmail() != null) {
                predicates.add(cb.like(
                    cb.lower(root.get("email")),
                    "%" + request.getEmail().toLowerCase() + "%")
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

