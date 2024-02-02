package com.ssafy.saessak.chat.dto;

import com.ssafy.saessak.chat.domain.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatMessageRequest {
    private Long roomId;
    private String chatContent;
    private Long senderId;
    private Long receiverId;
    LocalDateTime chatTime;
}
