package com.ssafy.saessak.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class MenuPhotoRequestDto {

    private Long menuId;
    private MultipartFile menuPath;

}
