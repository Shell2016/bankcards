package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for card status update {@link Card}
 */
public record CardStatusUpdateDto(
        @NotNull
        CardStatus status
) {
}