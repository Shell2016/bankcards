package com.example.bankcards.dto;

public record DeleteCardRequestDto(
        Long userId,
        String email,
        String password
) {
}
