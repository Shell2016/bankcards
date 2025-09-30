package com.example.bankcards.controller;

import com.example.bankcards.dto.CardCreateDto;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardUpdateDto;
import com.example.bankcards.service.CardFilter;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Tag(name = "Cards", description = "Cards management API")
public class CardController {

    private final CardService cardService;

    @PostMapping
    @Operation(summary = "Create card", description = "Create a new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public CardDto create(@RequestBody @Valid CardCreateDto dto) {
        return cardService.create(dto);
    }

    @GetMapping
    @Operation(summary = "Get all cards by filter(email)", description = "Retrieve all cards with pagination and filter(email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards retrieved successfully")
    })
    public PagedModel<CardDto> getAll(@ParameterObject @ModelAttribute CardFilter filter,
                                      @ParameterObject Pageable pageable) {
        Page<CardDto> cardDtos = cardService.getAll(filter, pageable);
        return new PagedModel<>(cardDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get card by ID", description = "Retrieve a card by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public CardDto getOne(@PathVariable Long id) {
        return cardService.getOne(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update card balance and/or status", description = "Update card status and balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card updated successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public CardDto update(@PathVariable Long id, @RequestBody CardUpdateDto dto) {
        return cardService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete card", description = "Delete a card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public void delete(@PathVariable Long id) {
        cardService.delete(id);
    }
}

