package com.spart.todolist.domain.card.dto;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.comment.dto.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectCardResponseDto {

    private String title;
    private String content;
    private String username;
    private LocalDateTime date;
    private Boolean complete;
    private List<CommentResponseDto> commentList;

    public SelectCardResponseDto(Card card,List<CommentResponseDto> responseListDtoList) {
        this.title = card.getTitle();
        this.content = card.getContent();
        this.username = card.getUser().getUsername();
        this.date = card.getModifiedAt();
        this.complete = card.getComplete();
        this.commentList = responseListDtoList;
    }

}
