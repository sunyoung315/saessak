package com.ssafy.saessak.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParentRegistRequestDto {
    private String parentName;
    private String parentEmail;
    private String parentPassword;
}
