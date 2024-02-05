package com.ssafy.saessak.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class NoticeResponseDto {
    private Long noticeId;
    private String noticeTitle;
    private LocalDate noticeTime;
    private boolean noticeFlag;
    private boolean fileFlag;
}
