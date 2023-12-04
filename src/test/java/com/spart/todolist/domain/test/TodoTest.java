package com.spart.todolist.domain.test;


import com.spart.todolist.domain.card.dto.CardRequestDto;
import com.spart.todolist.domain.card.dto.CreateCardResponseDto;
import com.spart.todolist.domain.card.dto.SelectCardResponseDto;
import com.spart.todolist.domain.card.dto.UpdateCardResponseDto;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.comment.dto.CommentResponseDto;
import com.spart.todolist.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface TodoTest extends CommonTest {

    Long TEST_TODO_ID = 1L;
    String TEST_TODO_TITLE = "title";
    String TEST_TODO_CONTENT = "content";
    String TEST_COMMENT_CONTENT = "comment";
    Boolean DEFAULT_COMPLETE = false;
    LocalDateTime DATE_TIME = LocalDateTime.now();

    CardRequestDto TEST_TODO_REQUEST_DTO = CardRequestDto.builder()
        .title(TEST_TODO_TITLE)
        .content(TEST_TODO_CONTENT)
        .build();

    CreateCardResponseDto TEST_TODO_CREATE_RESPONSE_DTO = CreateCardResponseDto.builder()
        .title(TEST_TODO_TITLE)
        .content(TEST_TODO_CONTENT)
        .username(CommonTest.TEST_USER_NAME)
        .date(DATE_TIME)
        .complete(DEFAULT_COMPLETE)
        .build();

    UpdateCardResponseDto TEST_TODO_UPDATE_RESPONSE_DTO = UpdateCardResponseDto.builder()
        .title(TEST_TODO_TITLE)
        .content(TEST_TODO_CONTENT)
        .username(CommonTest.TEST_USER_NAME)
        .date(DATE_TIME)
        .complete(DEFAULT_COMPLETE)
        .build();

    SelectCardResponseDto TEST_TODO_SELECT_RESPONSE_DTO = SelectCardResponseDto.builder()
        .title(TEST_TODO_TITLE)
        .content(TEST_TODO_CONTENT)
        .username(CommonTest.TEST_USER_NAME)
        .date(DATE_TIME)
        .complete(DEFAULT_COMPLETE)
        .build();
    Card TEST_TODO = Card.builder()
        .title(TEST_TODO_TITLE)
        .content(TEST_TODO_CONTENT)
        .user(CommonTest.TEST_USER)
        .complete(DEFAULT_COMPLETE)
        .build();
    Card TEST_ANOTHER_TODO = Card.builder()
        .title(ANOTHER_PREFIX + TEST_TODO_TITLE)
        .content(ANOTHER_PREFIX + TEST_TODO_CONTENT)
        .user(CommonTest.TEST_ANOTHER_USER)
        .complete(DEFAULT_COMPLETE)
        .build();

    Comment COMMENT = Comment.builder()
        .card(TEST_TODO)
        .user(TEST_USER)
        .content(TEST_COMMENT_CONTENT)
        .build();
}
