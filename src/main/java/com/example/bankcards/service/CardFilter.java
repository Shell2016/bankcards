package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record CardFilter(String userEmail) {
    public Specification<Card> toSpecification() {
        return userEmailSpec();
    }

    private Specification<Card> userEmailSpec() {
        return ((root, query, cb) -> StringUtils.hasText(userEmail)
                ? cb.equal(root.get("user").get("email"), userEmail)
                : null);
    }
}