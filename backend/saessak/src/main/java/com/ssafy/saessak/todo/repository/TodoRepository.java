package com.ssafy.saessak.todo.repository;

import com.ssafy.saessak.todo.domain.Todo;
import com.ssafy.saessak.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    Optional<List<Todo>> findAllByUserOrderByTodoId(User user);
    void deleteAllByCreatedDateBefore(LocalDate date);
}
