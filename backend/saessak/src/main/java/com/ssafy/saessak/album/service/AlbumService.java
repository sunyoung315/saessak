package com.ssafy.saessak.album.service;


import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.domain.File;
import com.ssafy.saessak.album.dto.AlbumResponseDto;
import com.ssafy.saessak.album.dto.FileResponseDto;
import com.ssafy.saessak.album.dto.KidAlbumResponseDto;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ClassroomRepository classroomRepository;
    private final KidRepository kidRepository;

    //엘범 조회
    public List<AlbumResponseDto> getClassAlbumList (Long classroomId){
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Album> albumList = albumRepository.findByClassroomAndKidIsNull(classroom).get();
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

    public List<KidAlbumResponseDto> getKidsCurrentAlbum (Long classroomId){
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Kid> kids = kidRepository.findAllByClassroom(classroom);
        List<KidAlbumResponseDto> albumList = new ArrayList<>();
        for(Kid kid : kids){
            Optional<Album> album = albumRepository.findFirstByKidOrderByAlbumDateDesc(kid);
            KidAlbumResponseDto kidAlbumResponseDto = KidAlbumResponseDto.builder()
                    .kidId(kid.getId())
                    .kidName(kid.getNickname())
                    .albumResponseDto(album.isPresent()? makeAlbumResponseDto(album.get()) : null)
                    .build();
            albumList.add(kidAlbumResponseDto);
        }

        return albumList;
    }


    public List<AlbumResponseDto> getClassAlbum(Long classroomId, Date date){
        Classroom classroom = classroomRepository.findById(classroomId).get();
        List<Album> albumList = albumRepository.findByClassroomAndAlbumDateAndKidIsNull(classroom, date).get();
        return makeAlbumResponseDtoList(albumList);
    }


    private AlbumResponseDto makeAlbumResponseDto(Album album){
        if(album == null) return null;
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
        return albumResponseDto;
    }
    private List<AlbumResponseDto> makeAlbumResponseDtoList(List<Album> albumList){
        List<AlbumResponseDto> albumResponseDtoList = new ArrayList<>();
        for(Album album : albumList){
            albumResponseDtoList.add(makeAlbumResponseDto(album));
        }
        return albumResponseDtoList;
    }
}
