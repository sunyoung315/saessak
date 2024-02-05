package com.ssafy.saessak.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class NoticeListRequestDto {
    private Long noticeId;
    private Long kidId;
}
