package com.example.morago.specification;

import com.example.morago.controller.dto.requests.translator.TranslatorGetRequest;
import com.example.morago.model.entity.Translator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TranslatorSpecification {

    public static Specification<Translator> build(TranslatorGetRequest request) {
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

            if (request.getIsOnline() != null) {
                predicates.add(cb.equal(root.get("isOnline"), request.getIsOnline()));
            }

            if (request.getLevelOfKorean() != null) {
                predicates.add(cb.equal(root.get("levelOfKorean"), request.getLevelOfKorean()));
            }

            if (request.getDateOfBirthFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateOfBirth"), request.getDateOfBirthFrom()));
            }

            if (request.getDateOfBirthTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateOfBirth"), request.getDateOfBirthTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

