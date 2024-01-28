package com.ssafy.saessak.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class KidResponseDto {

    private Long classroomId;
    private Long parentId;
    private Long kidId;
    private String kidName;

}
