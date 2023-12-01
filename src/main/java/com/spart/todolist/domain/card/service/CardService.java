package com.spart.todolist.domain.card.service;

import com.spart.todolist.domain.card.dto.CardRequestDto;
import com.spart.todolist.domain.card.dto.CardResponseDto;
import com.spart.todolist.domain.card.dto.CreateCardResponseDto;
import com.spart.todolist.domain.card.dto.UpdateCardResponseDto;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Transactional
    public CreateCardResponseDto createCard(CardRequestDto requestDto, User user) {
        Card card = Card.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user)
            .build();
        cardRepository.save(card);
        return new CreateCardResponseDto(card);
    }

    public List<CardResponseDto> getCardList() {
        List<Card> cardList = cardRepository.findAll();
        List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        for (Card card : cardList) {
            cardResponseDtoList.add(new CardResponseDto(card));
        }
        return cardResponseDtoList;

    }

    public List<CardResponseDto> getMyCardList(User user) {
        List<Card> cardList = cardRepository.findAllByUser(user);
        List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        for (Card card : cardList) {
            cardResponseDtoList.add(new CardResponseDto(card));
        }
        return cardResponseDtoList;
    }

    public List<CardResponseDto> getUsersCardList(User user) {
        List<Card> cardList = cardRepository.findAllByUserNot(user);
        List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        for (Card card : cardList) {
            cardResponseDtoList.add(new CardResponseDto(card));
        }
        return cardResponseDtoList;
    }

    @Transactional
    public UpdateCardResponseDto updateCard(Long cardId,CardRequestDto requestDto, User user) {
        Card card = findCardId(cardId);
        findUsername(card,user.getUsername());
        card.update(requestDto);
        return new UpdateCardResponseDto(card);
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        Card card = findCardId(cardId);
        findUsername(card,user.getUsername());
        cardRepository.delete(card);
    }

    ///////////////////////////////////////////////////////////////////
    private Card findCardId(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new NullPointerException("해당 게시물이 존재하지 않습니다."));
    }

    private void findUsername(Card card, String username) {
        if (!card.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("해당 유저정보가 일치 하지않습니다.");
        }
    }

    ///////////////////////////////////////////////////////////////////
}
