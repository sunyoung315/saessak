package com.ssafy.saessak.todo.dto;

import com.ssafy.saessak.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
public class TodoResponseDto {

    private Long todoId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private String content;
    private Boolean completed;
}
