package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record UserFilter(
        String email,
        String firstName,
        String lastNameStarts
) {
    public Specification<User> toSpecification() {
        return emailSpec()
                .or(firstNameSpec())
                .or(lastNameStartsSpec());
    }

    private Specification<User> emailSpec() {
        return ((root, query, cb) -> StringUtils.hasText(email)
                ? cb.equal(root.get("email"), email)
                : null);
    }

    private Specification<User> firstNameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(firstName)
                ? cb.equal(cb.lower(root.get("firstName")), firstName.toLowerCase())
                : null);
    }

    private Specification<User> lastNameStartsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(lastNameStarts)
                ? cb.like(cb.lower(root.get("lastName")), lastNameStarts.toLowerCase() + "%")
                : null);
    }
}