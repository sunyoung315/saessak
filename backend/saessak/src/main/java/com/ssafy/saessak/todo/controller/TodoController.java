package com.ssafy.saessak.todo.controller;

import com.ssafy.saessak.result.ResultCode;
import com.ssafy.saessak.result.ResultResponse;
import com.ssafy.saessak.todo.service.TodoService;
import com.ssafy.saessak.todo.dto.TodoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    @PostMapping("")
    public ResponseEntity<ResultResponse> saveTodo(@RequestParam(name = "content") String content){

        Long todoId = todoService.saveTodo(content);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, todoId));
    }
    @GetMapping("")
    public ResponseEntity<ResultResponse> getTodoList() {
        List<TodoResponseDto> todoList = todoService.getTodoList();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, todoList));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<ResultResponse> deleteTodo(@PathVariable(name = "todoId") Long todoId) {
        Long deletedTodo = todoService.deleteTodo(todoId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,deletedTodo));

    }
    @PatchMapping("/{todoId}")
    public ResponseEntity<ResultResponse> toggleTodo( @PathVariable(name = "todoId") Long todoId){
        Long patchedTodo = todoService.toggleTodo(todoId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS,patchedTodo));
    }
}
