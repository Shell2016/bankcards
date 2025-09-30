package com.example.bankcards.exception;

public class BalanceValidationException extends RuntimeException {
    public BalanceValidationException(String message) {
        super(message);
    }
}
