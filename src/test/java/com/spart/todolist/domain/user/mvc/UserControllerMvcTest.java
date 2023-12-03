package com.spart.todolist.domain.user.mvc;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.todolist.domain.user.controller.UserController;
import com.spart.todolist.domain.user.dto.UserRequestDto;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.service.UserService;
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

@DisplayName("UserController 테스트")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(
    controllers = {UserController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class UserControllerMvcTest{
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "dongmin0";
        String password = "Qwer1234";
        User testUser = new User(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("회원가입")
    void signup() throws Exception {
        String username = "dongmin0";
        String password = "Qwer1234";
        UserRequestDto requestDto = new UserRequestDto(username,password);
        String json = objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/api/users/signup")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        String username = "dongmin0";
        String password = "Qwer1234";
        UserRequestDto requestDto = new UserRequestDto(username,password);
        String json = objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/api/users/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }



}
