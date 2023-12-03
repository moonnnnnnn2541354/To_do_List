package com.spart.todolist.domain.card.controller;

import com.spart.todolist.domain.card.dto.CardListResponseDto;
import com.spart.todolist.domain.card.dto.CardRequestDto;
import com.spart.todolist.domain.card.dto.CreateCardResponseDto;
import com.spart.todolist.domain.card.dto.SelectCardResponseDto;
import com.spart.todolist.domain.card.dto.UpdateCardResponseDto;
import com.spart.todolist.domain.card.service.CardService;
import com.spart.todolist.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CreateCardResponseDto> createCard(@RequestBody CardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        CreateCardResponseDto responseDto = cardService.createCard(requestDto,
            userDetailsImpl.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<SelectCardResponseDto> getCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        SelectCardResponseDto cardResponseDto = cardService.getCard(cardId, userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CardListResponseDto>> getCardList() {
        List<CardListResponseDto> cardResponseDtoList = cardService.getCardList();
        return ResponseEntity.ok().body(cardResponseDtoList);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<CardListResponseDto>> getMyCardList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CardListResponseDto> cardResponseDtoList = cardService.getMyCardList(
            userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDtoList);
    }

    @GetMapping("/users")
    public ResponseEntity<List<CardListResponseDto>> getUsersCardList(
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        List<CardListResponseDto> cardResponseDtoList = cardService.getUsersCardList(
            userDetailsImpl.getUser());
        return ResponseEntity.ok().body(cardResponseDtoList);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<UpdateCardResponseDto> updateCard(
        @PathVariable(name = "cardId") Long cardId,
        @RequestBody CardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UpdateCardResponseDto updateCardResponseDto = cardService.updateCard(cardId, requestDto,
            userDetails.getUser());

        return ResponseEntity.ok().body(updateCardResponseDto);
    }

    @PutMapping("/{cardId}/end")
    public ResponseEntity<UpdateCardResponseDto> todoCompleted(
        @PathVariable(name = "cardId") Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UpdateCardResponseDto updateCardResponseDto = cardService.todoCompleted(cardId,
            userDetails.getUser());
        return ResponseEntity.ok().body(updateCardResponseDto);
    }

    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(cardId, userDetails.getUser());
    }
}
