package com.ssafy.saessak.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatPagingRequestDto {
    private String cursor;
}
