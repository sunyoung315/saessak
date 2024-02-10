package com.ssafy.saessak.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class NoticeResponseDto implements Comparable<NoticeResponseDto>  {
    private Long noticeId;
    private String noticeTitle;
    private LocalDate noticeTime;
    private String teacherName;
    private boolean noticeFlag;
    private boolean fileFlag;
    @Override
    public int compareTo(NoticeResponseDto other) {
        return other.noticeId.compareTo(this.noticeId);
    }
}
