package com.spart.todolist.domain.user.controller;

import com.spart.todolist.domain.user.dto.UserRequestDto;
import com.spart.todolist.domain.user.service.UserService;
import com.spart.todolist.global.dto.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}
