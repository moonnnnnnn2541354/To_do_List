package com.spart.todolist.domain.card.repository;

import com.spart.todolist.domain.card.entity.Card;
import com.spart.todolist.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {


    List<Card> findAllByUserOrderByCreatedAtDesc(User user);

    List<Card> findAllByUserNotOrderByCreatedAtDesc(User user);

    List<Card> findAllByOrderByCreatedAtDesc();


}
