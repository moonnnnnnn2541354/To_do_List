package com.spart.todolist.domain.comment.entity;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.comment.dto.CommentRequestDto;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.utils.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Comment(String content, Card card, User user) {
        this.content = content;
        this.card = card;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto, Card card, User user){
        this.content = requestDto.getContent();
        this.card = card;
        this.user = user;
    }


}
