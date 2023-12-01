package com.spart.todolist.domain.card.dto;

import com.spart.todolist.domain.comment.entity.Comment;
import com.spart.todolist.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String content;
    private String username;
    private LocalDateTime date;

    public CommentResponseDto(Comment comment, String username) {
        this.content = comment.getContent();
        this.username = username;
        this.date = comment.getModifiedAt();
    }

}
