package com.spart.todolist.domain.comment.service;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.comment.dto.CommentRequestDto;
import com.spart.todolist.domain.comment.dto.CreateCommentResponseDto;
import com.spart.todolist.domain.comment.dto.UpdateCommentResponseDto;
import com.spart.todolist.domain.comment.entity.Comment;
import com.spart.todolist.domain.comment.repository.CommentRepository;
import com.spart.todolist.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentSerivce {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    @Transactional
    public CreateCommentResponseDto createComment(Long cardId, User user,
        CommentRequestDto requestDto) {
        Card card = findCard(cardId);
        checkUser(card, user.getUsername());
        Comment comment = Comment.builder()
            .content(requestDto.getContent())
            .user(user)
            .card(card)
            .build();
        commentRepository.save(comment);
        return new CreateCommentResponseDto(comment, user);
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long cardId, Long commentId, User user, CommentRequestDto requestDto) {
        Comment comment = findComment(commentId);
        Card card = findCard(cardId);
        checkUser(card,user.getUsername());
        comment.update(requestDto,card,user);
        return new UpdateCommentResponseDto(comment,user.getUsername());
    }

    @Transactional
    public void deleteComment(Long cardId, Long commentId, User user) {
        Comment comment = findComment(commentId);
        Card card = findCard(cardId);
        checkUser(card,user.getUsername());
        commentRepository.delete(comment);
    }

    ///////////////////////////////////////////////////////////////////
    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
    }
    private Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new NullPointerException("해당 게시물이 존재하지 않습니다."));
    }

    private void checkUser(Card card, String username) {
        if (!card.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }
    }


    ///////////////////////////////////////////////////////////////////
}
