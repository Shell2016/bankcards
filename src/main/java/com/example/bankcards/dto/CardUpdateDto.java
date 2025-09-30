package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;

import java.math.BigDecimal;

/**
 * DTO for card update {@link Card}
 */
public record CardUpdateDto(
        CardStatus status,
        BigDecimal balance
) {
}