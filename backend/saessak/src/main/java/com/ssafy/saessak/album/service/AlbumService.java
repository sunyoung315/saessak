package com.ssafy.saessak.album.service;


import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.repository.AlbumRepository;
import com.ssafy.saessak.album.repository.FileRepository;
import com.ssafy.saessak.user.domain.Classroom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FileRepository fileRepository;

    //엘범 조회
    public List<Album> getClassAlbum (Classroom classroom){

        return albumRepository.findByClassroom(classroom);
    }

}
