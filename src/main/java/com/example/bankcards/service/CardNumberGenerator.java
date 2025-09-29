package com.example.bankcards.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CardNumberGenerator {
    private final SecureRandom random = new SecureRandom();
    private static final int PAN_LENGTH = 16;

    public String generatePan() {
        StringBuilder sb = new StringBuilder(PAN_LENGTH);
        for (int i = 0; i < PAN_LENGTH - 1; i++) sb.append(random.nextInt(10));
        int checkDigit = computeLuhnCheckDigit(sb.toString());
        sb.append(checkDigit);
        return sb.toString();
    }

    private int computeLuhnCheckDigit(String withoutCheckDigit) {
        int sum = 0;
        boolean doubleIt = true;
        for (int i = withoutCheckDigit.length() - 1; i >= 0; i--) {
            int d = withoutCheckDigit.charAt(i) - '0';
            if (doubleIt) { d *= 2; if (d > 9) d -= 9; }
            sum += d;
            doubleIt = !doubleIt;
        }
        return (10 - (sum % 10)) % 10;
    }
}

