package com.spart.todolist.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.spart.todolist.domain.card.dto.CreateCardResponseDto;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.card.service.CardService;
import com.spart.todolist.domain.test.TodoTest;
import com.spart.todolist.domain.test.TodoTestUtils;

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

}
