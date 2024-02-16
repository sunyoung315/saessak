package com.ssafy.saessak.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class RoomResponseDto {
    private Long roomId;
    private Long kidId;
    private String kidName;
    private String teacherName;
    private String lastChat;
    private boolean flag; // 이미 다 읽었으면 true
    private String kidProfile;
    private String teacherProfile;
}
