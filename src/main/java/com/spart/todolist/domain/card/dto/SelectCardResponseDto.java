package com.spart.todolist.domain.card.dto;

import com.spart.todolist.domain.card.entity.Card;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class SelectCardResponseDto {

    private String title;
    private String content;
    private String username;
    private LocalDateTime date;
    private List<CommentResponseDto> commentList;

    public SelectCardResponseDto(Card card,List<CommentResponseDto> responseListDtoList) {
        this.title = card.getTitle();
        this.content = card.getContent();
        this.username = card.getUser().getUsername();
        this.date = card.getModifiedAt();
        this.commentList = responseListDtoList;
    }

}
