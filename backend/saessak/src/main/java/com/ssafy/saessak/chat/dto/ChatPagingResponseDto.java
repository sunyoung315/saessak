package com.ssafy.saessak.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatPagingResponseDto {
    private Long roomId;
    private String chatContent;
    private Long senderId;
    private String chatTime;
}