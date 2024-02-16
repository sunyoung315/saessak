package com.ssafy.saessak.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class NoticeDetailReponseDto {
    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDate noticeTime;
    private String noticeFile;
    private String noticeFileName;
}
