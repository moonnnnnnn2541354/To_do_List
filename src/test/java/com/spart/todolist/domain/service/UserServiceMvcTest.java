package com.spart.todolist.domain.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.spart.todolist.domain.user.dto.UserRequestDto;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.repository.UserRepository;
import com.spart.todolist.domain.user.service.UserService;
import com.spart.todolist.global.jwt.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("UserService 테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceMvcTest {

    @Mock
    JwtUtil jwtUtil;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("회원가입")
    void signup() throws Exception {
        String username = "dongmin0";
        String password = "Qwer1234";
        UserRequestDto requestDto = new UserRequestDto(username,password);
        User user = User.builder()
            .username(requestDto.getUsername())
            .password(requestDto.getPassword())
            .build();
        given(userRepository.findByUsername(username)).willReturn(null);
        given(userRepository.save(any(User.class))).willReturn(user);
        userService.signup(requestDto);


        assertThat(requestDto.getUsername()).isEqualTo(username);
    }


}
