package com.ssafy.saessak.todo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoRequestDto {
    private String content;

}
