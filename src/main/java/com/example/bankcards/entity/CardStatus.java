package com.example.bankcards.entity;

import lombok.Getter;

@Getter
public enum CardStatus {
    ACTIVE("Активна"),
    BLOCKED("Заблокирована"),
    EXPIRED("Истек срок");

    private final String displayName;

    CardStatus(String displayName) {
        this.displayName = displayName;
    }
}
