package com.spart.todolist.domain.card.dto;

import com.spart.todolist.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class CardResponseDto {

    private String title;
    private String content;
    private String username;

    public CardResponseDto(Card card) {
        this.title = card.getTitle();
        this.content = card.getContent();
        this.username = card.getUser().getUsername();
    }
}
