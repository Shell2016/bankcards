package com.example.bankcards.service;

import com.example.bankcards.dto.UserCreateDto;
import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public UserDto create(UserCreateDto dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id `%s` not found".formatted(id)));
    }

    public Page<UserDto> getAllByFilter(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toUserDto);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "User with id `%s` not found".formatted(id)
                ));
        userRepository.delete(user);
    }

    public UserDto updateRole(Long id, com.example.bankcards.entity.Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "User with id `%s` not found".formatted(id)
                ));
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }


}
