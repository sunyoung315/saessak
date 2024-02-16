package com.ssafy.saessak.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class NoticeResponseFixedDto {
    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDate noticeTime;
    private boolean noticeFlag;
    private boolean fileFlag;
}
