package com.ssafy.saessak.album.service;


import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.repository.AlbumRepository;
import com.ssafy.saessak.album.repository.FileRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FileRepository fileRepository;

    //엘범 조회
    public List<Album> getClassAlbumList (Long classroomId){
        Classroom classroom = Classroom.builder().classroomId(classroomId).build();
        return albumRepository.findByClassroom(classroom);
    }

    public List<Album> getKidAlbumList (Kid kid){
        return albumRepository.findByKid(kid);
    }

    public Album getKidAlbum(Kid kid, Date date){
        return albumRepository.findByKidAndAlbumDate(kid, date);
    }

    public Album getClassAlbum(Classroom classroom, Date date){
        return albumRepository.findByClassroomAndAlbumDate(classroom,date);

    }

}
