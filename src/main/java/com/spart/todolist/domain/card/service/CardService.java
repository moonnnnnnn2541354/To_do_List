package com.spart.todolist.domain.card.service;

import com.spart.todolist.domain.card.dto.CardRequestDto;
import com.spart.todolist.domain.card.dto.CardResponseDto;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public CardResponseDto createCard(CardRequestDto requestDto, User user) {
        Card card = Card.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user)
            .build();
        cardRepository.save(card);
        CardResponseDto responseDto = new CardResponseDto(card);
        return responseDto;
    }

}
