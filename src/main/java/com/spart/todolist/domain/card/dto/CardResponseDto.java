package com.spart.todolist.domain.card.dto;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.user.entity.User;
import lombok.Getter;

@Getter
public class CardResponseDto {

    private String title;
    private String content;
    private String username;

    public CardResponseDto(Card card) {
        this.title = card.getTitle();
        this.content = getContent();
        this.username = card.getUser().getUsername();
    }
}
