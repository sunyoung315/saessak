package com.ssafy.saessak.todo.util;

import com.ssafy.saessak.todo.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class TodoBackScheduling {
    private final TodoRepository todoRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteYesterdayTodoList(){
        LocalDate today = LocalDate.now();
        log.info("delete yesterday todoList, today is {}", today);
        todoRepository.deleteAllByCreatedDateBefore(today);

    }

}
