package com.spart.todolist.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.spart.todolist.domain.card.dto.CreateCardResponseDto;
import com.spart.todolist.domain.card.dto.SelectCardResponseDto;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.card.service.CardService;
import com.spart.todolist.domain.comment.dto.CommentResponseDto;
import com.spart.todolist.domain.test.TodoTest;
import com.spart.todolist.domain.test.TodoTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("UserService 테스트")
@ExtendWith(MockitoExtension.class)
public class CardServiceTest implements TodoTest {

    @InjectMocks
    CardService cardService;

    @Mock
    CardRepository cardRepository;
    
    @DisplayName("할일카드 생성")
    @Test
    void createCard() {
        Card card = TodoTestUtils.get(TEST_TODO,TEST_USER);
        given(cardRepository.save(any(Card.class))).willReturn(card);

        CreateCardResponseDto responseDto = cardService.createCard(TEST_TODO_REQUEST_DTO,TEST_USER);
        CreateCardResponseDto expect = new CreateCardResponseDto(card);

        assertThat(responseDto).isEqualTo(expect);
    }

    @DisplayName("할일 DTO 조회")
    @Test
    void getCard() {
        Card card = TodoTestUtils.get(TEST_TODO, TEST_USER);
        List<CommentResponseDto> commentList = Arrays.asList(
            new CommentResponseDto(COMMENT,TEST_USER.getUsername()));
        given(cardRepository.findById(eq(TEST_TODO_ID))).willReturn(Optional.of(card));
        given(cardService.commentList(any(Card.class))).willReturn(commentList);

        SelectCardResponseDto result = cardService.getCard(TEST_TODO_ID,TEST_USER);

        assertThat(result).isEqualTo(new SelectCardResponseDto(card,commentList));
    }



}
