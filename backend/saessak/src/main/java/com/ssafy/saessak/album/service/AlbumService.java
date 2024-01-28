package com.ssafy.saessak.album.service;


import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.domain.File;
import com.ssafy.saessak.album.dto.AlbumResponseDto;
import com.ssafy.saessak.album.dto.FileResponseDto;
import com.ssafy.saessak.album.repository.AlbumRepository;
import com.ssafy.saessak.album.repository.FileRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final FileRepository fileRepository;
    private final ClassroomRepository classroomRepository;
    private final KidRepository kidRepository;
    //엘범 조회
    public List<AlbumResponseDto> getClassAlbumList (Long classroomId){
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Album> albumList = albumRepository.findByClassroom(classroom).get();
        return makeAlbumResponseDtoList(albumList);
    }

    public List<AlbumResponseDto> getKidAlbumList (Long kidId){
        Kid kid = kidRepository.findById(kidId).get();
        List<Album> albumList = albumRepository.findByKid(kid).get();
        return makeAlbumResponseDtoList(albumList);
    }

    public List<AlbumResponseDto> getKidAlbum(Long kidId, Date date){
        Kid kid = kidRepository.findById(kidId).get();
        List<Album> albumList = albumRepository.findByKidAndAlbumDate(kid,date).get();
        return makeAlbumResponseDtoList(albumList);
    }

    public List<AlbumResponseDto> getClassAlbum(Long classroomId, Date date){
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Album> albumList = albumRepository.findByClassroomAndAlbumDate(classroom, date).get();
        return makeAlbumResponseDtoList(albumList);
    }

    private List<AlbumResponseDto> makeAlbumResponseDtoList(List<Album> albumList){
        List<AlbumResponseDto> albumResponseDtoList = new ArrayList<>();

        for(Album album : albumList){
            AlbumResponseDto albumResponseDto = AlbumResponseDto.builder()
                    .albumId(album.getAlbumId())
                    .albumTitle(album.getAlbumTitle())
                    .albumDate(album.getAlbumDate())
                    .fileResponseDtoList(new ArrayList<>())
                    .build();
            for(File file : album.getFileList()){
                FileResponseDto fileResponseDto = FileResponseDto.builder()
                        .fileId(file.getFileId())
                        .fileName(file.getFileName())
                        .filePath(file.getFilePath())
                        .build();
                albumResponseDto.getFileResponseDtoList().add(fileResponseDto);
            }
            albumResponseDtoList.add(albumResponseDto);
        }
        return albumResponseDtoList;
    }
}
