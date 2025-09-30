package com.example.bankcards.service;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class CardService {

    private final CardMapper cardMapper;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardNumberGenerator cardNumberGenerator;

    @Transactional
    public CardDto create(CardCreateDto dto) {
        Card card = cardMapper.toEntity(dto);
        User user = userRepository.findById(card.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        card.setUser(user);
        card.setStatus(CardStatus.ACTIVE);
        card.setExpiryDate(LocalDate.now().plusYears(3));
        card.setCardNumber(cardNumberGenerator.generatePan());
        Card savedCard = cardRepository.save(card);
        return cardMapper.toCardDto(savedCard);
    }

    public Page<CardDto> getAll(CardFilter filter, Pageable pageable) {
        Specification<Card> spec = filter.toSpecification();
        Page<Card> cards = cardRepository.findAll(spec, pageable);
        return cards.map(cardMapper::toCardDto);
    }

    public CardDto getOne(Long id) {
        return cardRepository.findById(id)
                .map(cardMapper::toCardDto)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Card with id `%s` not found".formatted(id)));
    }

    @Transactional
    public CardDto update(Long id, CardBalanceUpdateDto dto) {
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        cardMapper.updateCardBalance(dto, card);
        Card resultCard = cardRepository.save(card);
        return cardMapper.toCardDto(resultCard);
    }

    @Transactional
    public CardDto update(Long id, CardStatusUpdateDto dto) {
        Card card = cardRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        cardMapper.updateCardStatus(dto, card);
        Card resultCard = cardRepository.save(card);
        return cardMapper.toCardDto(resultCard);
    }

    @Transactional
    public void delete(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Card with id `%s` not found".formatted(id)
                ));
        cardRepository.delete(card);
    }
}
