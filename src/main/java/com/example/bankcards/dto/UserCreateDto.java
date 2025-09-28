package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creation of {@link com.example.bankcards.entity.User}
 */
public record UserCreateDto(
        @Email @NotBlank
        String email,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String password,

        @NotNull
        Role role
) {
}