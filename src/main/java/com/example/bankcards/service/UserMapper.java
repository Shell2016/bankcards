package com.example.bankcards.service;

import com.example.bankcards.dto.UserCreateDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateDto userCreateDto);

    UserCreateDto toUserCreateDto(User user);

    User toEntity(UserDto userDto);

    UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cards", ignore = true)
    void updateWithNull(UserDto userDto, @MappingTarget User user);
}