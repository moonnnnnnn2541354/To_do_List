package com.spart.todolist.domain.user.controller;

import com.spart.todolist.domain.user.dto.UserRequestDto;
import com.spart.todolist.domain.user.service.UserService;
import com.spart.todolist.global.dto.CommonResponseDto;
import com.spart.todolist.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto("회원가입 실패", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@Valid @RequestBody UserRequestDto requestDto,
        HttpServletResponse response) {
        try {
            userService.login(requestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto("로그인 실패", HttpStatus.BAD_REQUEST.value()));
        }
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER,
            jwtUtil.createToken(requestDto.getUsername()));
        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }
}
