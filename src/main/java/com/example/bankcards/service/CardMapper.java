package com.example.bankcards.service;

import com.example.bankcards.dto.CardCreateDto;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardUpdateDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    @Mapping(source = "userId", target = "user.id")
    Card toEntity(CardCreateDto cardCreateDto);

    @Mapping(source = "user.id", target = "userId")
    CardCreateDto toCardCreateDto(Card card);

    Card toEntity(CardDto cardDto);

    @Mapping(source = "cardNumber", target = "maskedCardNumber", qualifiedByName = "maskPan")
    CardDto toCardDto(Card card);

    @Named("maskPan")
    default String maskPan(String pan) {
        if (pan == null) return null;
        String digits = pan.replaceAll("\\D", "");
        if (digits.length() <= 4) return pan;
        String last4 = digits.substring(digits.length() - 4);
        return "**** **** **** " + last4;
    }

    void updateCard(CardUpdateDto cardDto, @MappingTarget Card card);
}