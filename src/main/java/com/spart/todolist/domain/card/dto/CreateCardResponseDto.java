package com.spart.todolist.domain.card.dto;

import com.spart.todolist.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CreateCardResponseDto {

    private String title;
    private String content;
    private String username;
    private LocalDateTime date;
    private Boolean complete;

    public CreateCardResponseDto(Card card) {
        this.title = card.getTitle();
        this.content = card.getContent();
        this.username = card.getUser().getUsername();
        this.date = card.getCreatedAt();
        this.complete = false;
    }
}
