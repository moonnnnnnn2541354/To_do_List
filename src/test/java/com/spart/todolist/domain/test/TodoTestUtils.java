package com.spart.todolist.domain.test;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.user.entity.User;
import java.time.LocalDateTime;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.SerializationUtils;


public class TodoTestUtils {

    public static Card get(Card card, User user) {
        return get(card, 1L, LocalDateTime.now(), user);
    }

    /**
     * 테스트용 할일 객체를 만들어주는 메서드
     * @param card 복제할 할일 객체
     * @param id 설정할 아이디
     * @param createDate 설정할 생성일시
     * @param user 연관관계 유저
     * @return 테스트용으로 생성된 할일 객체
     */
    public static Card get(Card card, Long id, LocalDateTime createDate, User user) {
        var newTodo = SerializationUtils.clone(card);
        ReflectionTestUtils.setField(newTodo, Card.class, "id", id, Long.class);
        ReflectionTestUtils.setField(newTodo, Card.class, "createDate", createDate, LocalDateTime.class);
        newTodo.setUser(user);
        return newTodo;
    }
}
