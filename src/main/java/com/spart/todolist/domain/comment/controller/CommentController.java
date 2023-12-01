package com.spart.todolist.domain.comment.controller;

import com.spart.todolist.domain.comment.dto.CommentRequestDto;
import com.spart.todolist.domain.comment.dto.CreateCommentResponseDto;
import com.spart.todolist.domain.comment.dto.UpdateCommentResponseDto;
import com.spart.todolist.domain.comment.service.CommentSerivce;
import com.spart.todolist.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{cardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentSerivce commentSerivce;

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(@PathVariable Long cardId,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        CreateCommentResponseDto resuponseDto = commentSerivce.createComment(cardId,
            userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resuponseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(@PathVariable Long cardId,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CommentRequestDto requestDto) {
        UpdateCommentResponseDto responseDto = commentSerivce.updateComment(cardId, commentId,
            userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(responseDto);
    }
}
