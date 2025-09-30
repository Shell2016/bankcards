package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO for card balance update {@link Card}
 */
public record CardBalanceUpdateDto(
        @NotNull
        BigDecimal balance
) {
}