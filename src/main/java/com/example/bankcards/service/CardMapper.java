package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateDto;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    @Mapping(source = "userId", target = "user.id")
    Card toEntity(CardCreateDto cardCreateDto);

    @Mapping(source = "user.id", target = "userId")
    CardCreateDto toCardCreateDto(Card card);

    Card toEntity(CardDto cardDto);

    @InheritInverseConfiguration(name = "toEntity")
    CardDto toCardDto(Card card);
}