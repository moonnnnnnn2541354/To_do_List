package com.spart.todolist.global.security;

import com.spart.todolist.domain.user.entity.User;
import com.spart.todolist.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {
    private final UserRepository userRepository;
    public UserDetails getUserDetails(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("존재하지 않는 user 입니다.")
        );
        return new UserDetailsImpl(user);
    }

}
