package com.ssafy.saessak.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatPagingRequestDto {
    private Long roomId;
    private String chatContent;
    private Long senderId;
    private String chatTime;
}
