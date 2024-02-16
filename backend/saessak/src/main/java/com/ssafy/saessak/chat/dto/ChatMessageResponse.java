package com.ssafy.saessak.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private Long roomId;
    private String chatContent;
    private Long senderId;
    private String chatTime;
}
