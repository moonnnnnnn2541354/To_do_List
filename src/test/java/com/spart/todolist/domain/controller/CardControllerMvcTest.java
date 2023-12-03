package com.spart.todolist.domain.controller;

import static com.spart.todolist.domain.card.constant.PostConstant.DEFAULT_COMPLETE;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.todolist.domain.card.controller.CardController;
import com.spart.todolist.domain.card.dto.CardRequestDto;
import com.spart.todolist.domain.card.dto.CreateCardResponseDto;
import com.spart.todolist.domain.card.dto.UpdateCardResponseDto;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.card.repository.CardRepository;
import com.spart.todolist.domain.card.service.CardService;
import com.spart.todolist.domain.mvcfilter.MockSpringSecurityFilter;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.global.config.WebSecurityConfig;
import com.spart.todolist.global.jwt.JwtUtil;
import com.spart.todolist.global.security.UserDetailsImpl;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DisplayName("UserController 테스트")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
    controllers = {CardController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class CardControllerMvcTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CardService cardService;

    @MockBean
    CardRepository cardRepository;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        User kim = User.builder().username("kim12345").password("12345678").build();
        User dong = User.builder().username("dong12345").password("12345678").build();

        List<Card> cardList = Arrays.asList(
            new Card("나는 제",false,"나는 내",kim),
            new Card("나는 목",false,"나는 용",kim),
            new Card("나는 임",false,"나는 임",dong));

        given(cardRepository.findAll()).willReturn(cardList);
        given(cardRepository.findAllByOrderByCreatedAtDesc()).willReturn(cardList);

        UserDetailsImpl testUserDetails = new UserDetailsImpl(kim);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "kim";
        String password = "12345678";
        User testUser = new User(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("할일카드 생성")
    void createCard() throws Exception {
        User kim = User.builder().username("kim12345").password("12345678").build();
        CardRequestDto requestDto = new CardRequestDto("나는 제목","나는 내용");
        Card card = Card.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .complete(DEFAULT_COMPLETE)
            .user(kim)
            .build();
        CreateCardResponseDto responseDto = new CreateCardResponseDto(card);
        String json = objectMapper.writeValueAsString(responseDto);

        given(cardService.createCard(any(CardRequestDto.class), any(User.class)))
            .willReturn(responseDto);

        mvc.perform(post("/api/cards")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))

            .andExpect(status().isCreated())
            .andDo(print())
            .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
            .andExpect(jsonPath("$.content").value(responseDto.getContent()));
    }

    @Test
    @DisplayName("할일카드 단일조회")
    void getCard() throws Exception {
    }

    @Test
    @DisplayName("할일카드 전체조회")
    void getCardList() throws Exception {
    }

    @Test
    @DisplayName("할일카드 수정")
    void updateCard() throws Exception {
        User kim = User.builder().username("kim12345").password("12345678").build();
        Long cardId = 1L;
        CardRequestDto requestDto = new CardRequestDto("나는 제목수정","나는 내용수정");
        Card card = Card.builder()
            .title(requestDto.getTitle())
            .content(requestDto.getContent())
            .complete(DEFAULT_COMPLETE)
            .user(kim)
            .build();

        UpdateCardResponseDto responseDto = new UpdateCardResponseDto(card);
        String json = objectMapper.writeValueAsString(responseDto);

        given(cardService.updateCard(anyLong(),any(CardRequestDto.class),any(User.class)))
            .willReturn(responseDto);

        mvc.perform(put("/api/cards/{cardId}",cardId)
                .content(json)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("할일카드 완료")
    void todoCompleted() throws Exception {
        User kim = User.builder().username("kim12345").password("12345678").build();
        Long cardId = 1L;
        Card card = Card.builder()
            .title("나는 제목")
            .content("나는 내용")
            .complete(DEFAULT_COMPLETE)
            .user(kim)
            .build();
        card.complete();
        UpdateCardResponseDto responseDto = new UpdateCardResponseDto(card);
        String json = objectMapper.writeValueAsString(responseDto);

        given(cardService.todoCompleted(anyLong(),any(User.class)))
            .willReturn(responseDto);



        mvc.perform(put("/api/cards/{cardId}/end",cardId)
                .content(json)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("할일카드 삭제")
    void deleteCard() throws Exception {
    }
}
