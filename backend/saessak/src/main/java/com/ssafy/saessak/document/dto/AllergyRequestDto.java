package com.ssafy.saessak.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllergyRequestDto {

    private Long kidId;
    private String kidName;
    private String classroomName;
    private String kidAllergy;

}
