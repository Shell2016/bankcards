package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;

/**
 * DTO for {@link User}
 */
public record UserDto(Long id, String email, String firstName, String lastName, Role role) {
}