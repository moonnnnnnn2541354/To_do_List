package com.spart.todolist.domain.controller;

import static com.spart.todolist.domain.card.constant.PostConstant.DEFAULT_COMPLETE;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.comment.controller.CommentController;
import com.spart.todolist.domain.comment.dto.CommentRequestDto;
import com.spart.todolist.domain.comment.dto.CreateCommentResponseDto;
import com.spart.todolist.domain.comment.dto.UpdateCommentResponseDto;
import com.spart.todolist.domain.comment.entity.Comment;
import com.spart.todolist.domain.comment.service.CommentSerivce;
import com.spart.todolist.domain.mvcfilter.MockSpringSecurityFilter;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.global.config.WebSecurityConfig;
import com.spart.todolist.global.jwt.JwtUtil;
import com.spart.todolist.global.security.UserDetailsImpl;
import java.security.Principal;
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

@DisplayName("CommentController 테스트")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
    controllers = {CommentController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class CommentControllerMvcTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CommentSerivce commentSerivce;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        User kim = User.builder().username("kim12345").password("12345678").build();

        UserDetailsImpl testUserDetails = new UserDetailsImpl(kim);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }


    @Test
    @DisplayName("댓글 작성")
    void createComment() throws Exception {
        User kim = User.builder().username("kim12345").password("12345678").build();
        Long cardId = 1L;
        Card card = Card.builder()
            .title("나는 제목")
            .content("나는 내용")
            .complete(DEFAULT_COMPLETE)
            .user(kim)
            .build();

        CommentRequestDto requestDto = new CommentRequestDto("댓글");
        Comment comment = Comment.builder()
            .content(requestDto.getContent())
            .card(card)
            .user(kim)
            .build();
        CreateCommentResponseDto responseDto = new CreateCommentResponseDto(comment,kim);
        String json = objectMapper.writeValueAsString(requestDto);

        given(commentSerivce.createComment(anyLong(),any(User.class),any(CommentRequestDto.class)))
            .willReturn(responseDto);

        mvc.perform(post("/api/{cardId}/comments",cardId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        User kim = User.builder().username("kim12345").password("12345678").build();
        Long cardId = 1L;
        Card card = Card.builder()
            .title("나는 제목")
            .content("나는 내용")
            .complete(DEFAULT_COMPLETE)
            .user(kim)
            .build();
        Long commentId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto("댓글수정");
        Comment comment = Comment.builder()
            .content("댓글")
            .card(card)
            .user(kim)
            .build();
        UpdateCommentResponseDto responseDto = new UpdateCommentResponseDto(comment,kim.getUsername());
        String json = objectMapper.writeValueAsString(requestDto);

        given(commentSerivce.updateComment(anyLong(),anyLong(),any(User.class),any(CommentRequestDto.class)))
            .willReturn(responseDto);

        mvc.perform(put("/api/{cardId}/comments/{commentId}",cardId,commentId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {

    }



}
