package com.ssafy.saessak.notice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeRequestDto {
    private String noticeTitle;
    private String noticeContent;
}
