package com.spart.todolist.domain.user.service;

import com.spart.todolist.domain.user.dto.UserRequestDto;
import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.repository.UserRepository;
import com.spart.todolist.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void signup(UserRequestDto requestDto) {
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
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new NullPointerException("username 이 일치하지 않습니다."));


    }

    /////////////////////////////////////////////////////////////////
    public UserDetails getUserDetails(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("존재하지 않는 user 입니다.")
        );
        return new UserDetailsImpl(user);
    }
    /////////////////////////////////////////////////////////////////
}
