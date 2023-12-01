package com.spart.todolist.domain.user.service;

import com.spart.todolist.domain.user.dto.UserRequestDto;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signup(UserRequestDto requestDto) {
        log.warn("서비스");
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }

        User user = User.builder()
            .username(username)
            .password(password)
            .build();

        userRepository.save(user);
    }

    public void login(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new NullPointerException("username 이 일치하지 않습니다."));

        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new IllegalArgumentException("password 가 일치하지 않습니다.");
        }


    }
}
