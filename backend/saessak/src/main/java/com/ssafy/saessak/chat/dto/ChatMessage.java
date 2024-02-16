package com.ssafy.saessak.chat.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long roomId;
    private String chatContent;
    private Long senderId;
    private String chatTime;
}
