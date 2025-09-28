package com.example.bankcards.controller;

import com.example.bankcards.dto.UserCreateDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.service.UserFilter;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management API")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public UserDto create(@RequestBody @Valid UserCreateDto dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserDto getOneById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all users by filter", description = "Retrieve all users with pagination and filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public PagedModel<UserDto> getAllByFilter(@ParameterObject @ModelAttribute UserFilter filter,
                                              @ParameterObject Pageable pageable) {
        Page<UserDto> userDtos = userService.getAllByFilter(filter, pageable);
        return new PagedModel<>(userDtos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public void delete(@Parameter(description = "User ID") @PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user role", description = "Update user role by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserDto updateRole(
            @Parameter(description = "User ID") @PathVariable Long id, 
            @Parameter(description = "New role") @RequestParam Role role) {
        return userService.updateRole(id, role);
    }


}

