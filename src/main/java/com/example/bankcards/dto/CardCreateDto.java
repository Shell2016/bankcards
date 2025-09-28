package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * DTO for creation of {@link Card}
 */
public record CardCreateDto(
        Long userId,

        @PositiveOrZero
        BigDecimal balance
) {
}