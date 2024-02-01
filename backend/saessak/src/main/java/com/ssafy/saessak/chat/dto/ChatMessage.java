package com.ssafy.saessak.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long roomId;
    private String chatContent;
    private Long senderId;
    private Long receiverId;
    private String chatTime;
}
