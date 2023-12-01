package com.spart.todolist.domain.comment.dto;

import com.spart.todolist.domain.comment.entity.Comment;
import com.spart.todolist.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateCommentResponseDto {
    private String content;
    private String username;
    private LocalDateTime date;

    public CreateCommentResponseDto(Comment comment, User user) {
        this.content = comment.getContent();
        this.username = user.getUsername();
        this.date = comment.getCreatedAt();
    }

}
