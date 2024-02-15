package com.ssafy.saessak.todo.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.todo.domain.Todo;
import com.ssafy.saessak.todo.dto.TodoResponseDto;
import com.ssafy.saessak.todo.repository.TodoRepository;
import com.ssafy.saessak.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final AuthenticationService authenticationService;
    // create
    @Transactional
    public Long saveTodo (String content) {
        User user = authenticationService.getUserByAuthentication();
        Todo todo = Todo.builder()
                .content(content)
                .user(user)
                .completed(false)
                .build();
        todoRepository.save(todo);
        return todo.getTodoId();
    }
    // read
    public List<TodoResponseDto> getTodoList(){
        User user = authenticationService.getUserByAuthentication();
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();
        Optional<List<Todo>> todoResult = todoRepository.findAllByUserOrderByTodoId(user);

        for(Todo todo : todoResult.get()){
            TodoResponseDto todoResponseDto = TodoResponseDto.builder()
                    .content(todo.getContent())
                    .todoId(todo.getTodoId())
                    .completed(todo.getCompleted())
                    .createdDate(todo.getCreatedDate())
                    .build();
            todoResponseDtoList.add(todoResponseDto);
        }
        return todoResponseDtoList;
    }
    @Transactional
    // update
    public Long toggleTodo (Long todoId){
        User user = authenticationService.getUserByAuthentication();
        Optional<Todo> todoResult = todoRepository.findByUserAndTodoId(user,todoId);
        if (todoResult.isEmpty()) throw new NotFoundException(ExceptionCode.TODO_NOT_FOUND);
        Todo todo = todoResult.get();
        todo.toggleTodo();
        todoRepository.save(todo);
        return todoId;
    }
    // delete
    @Transactional
    public Long deleteTodo (Long todoId){
        User user = authenticationService.getUserByAuthentication();
        Optional<Todo> todoResult = todoRepository.findByUserAndTodoId(user,todoId);
        if (todoResult.isEmpty()) throw new NotFoundException(ExceptionCode.TODO_NOT_FOUND);
        Todo todo = todoResult.get();
        todoRepository.delete(todo);
        return todo.getTodoId();
    }



}
