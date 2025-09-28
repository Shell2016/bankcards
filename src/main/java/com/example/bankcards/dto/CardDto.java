package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Card}
 */
public record CardDto(
        Long id,
        String maskedCardNumber,
        LocalDate expiryDate,
        CardStatus status,
        BigDecimal balance
) {
}