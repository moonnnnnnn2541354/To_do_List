package com.spart.todolist.domain.comment.service;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.card.service.CardService;
import com.spart.todolist.domain.comment.dto.CommetRequestDto;
import com.spart.todolist.domain.comment.dto.CreateCommentResuponseDto;
import com.spart.todolist.domain.comment.entity.Comment;
import com.spart.todolist.domain.comment.repository.CommentRepository;
import com.spart.todolist.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentSerivce {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;


    public CreateCommentResuponseDto createComment(Long cardId, User user,
        CommetRequestDto requestDto) {
        Card card = findCard(cardId);
        checkUser(card,user.getUsername());
        Comment comment = Comment.builder()
            .content(requestDto.getContent())
            .user(user)
            .card(card)
            .build();
        commentRepository.save(comment);
        return new CreateCommentResuponseDto(comment,user);
    }



    ///////////////////////////////////////////////////////////////////
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
