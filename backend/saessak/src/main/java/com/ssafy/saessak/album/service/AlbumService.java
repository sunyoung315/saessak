package com.ssafy.saessak.album.service;


import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.domain.File;
import com.ssafy.saessak.album.dto.AlbumResponseDto;
import com.ssafy.saessak.album.dto.FileResponseDto;
import com.ssafy.saessak.album.dto.KidAlbumResponseDto;
import com.ssafy.saessak.album.repository.AlbumRepository;
import com.ssafy.saessak.album.repository.FileRepository;
import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.NotFoundException;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.oauth.service.AuthenticationService;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final AuthenticationService authenticationService;

    public AlbumResponseDto getTeacherCurrentAlbum() {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        Optional<Album> albumResult = albumRepository.findFirstByClassroomAndKidIsNullOrderByAlbumDateDesc(classroom);
        if(albumResult.isEmpty()) throw new NotFoundException(ExceptionCode.ALBUM_NOT_FOUND);
        return makeAlbumResponseDto(albumResult.get());
    }

    public List<LocalDate> getExistAlbumDate() {
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        Optional<List<Album>> albumResult = albumRepository.findByClassroomAndKidIsNullOrderByAlbumDateDesc(classroom);
        List<LocalDate> existDate = new ArrayList<>();
        if(albumResult.isEmpty()) return existDate;
        for( Album album : albumResult.get()){
            existDate.add(album.getAlbumDate());
        }

        return existDate;

    }

    public AlbumResponseDto getAlbumUsingId (Long albumId) {
        Optional<Album> albumResult = albumRepository.findById(albumId);
        if(albumResult.isEmpty()) throw new NotFoundException(ExceptionCode.ALBUM_NOT_FOUND);
        return makeAlbumResponseDto(albumResult.get());
    }

    //엘범 조회
    public List<AlbumResponseDto> getTeacherClassAlbumList (){
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        Optional<List<Album>> albumList = albumRepository.findByClassroomAndKidIsNullOrderByAlbumDateDesc(classroom);
        return albumList.map(this::makeAlbumResponseDtoList).orElse(null);
    }

    public List<AlbumResponseDto> getParentClassAlbumList(Long kidId) {
        Optional<Kid> kidResult = kidRepository.findById(kidId);
        if(kidResult.isEmpty()) throw new UserException(ExceptionCode.KID_NOT_FOUND);
        Kid kid = kidResult.get();

        Classroom classroom = kid.getClassroom();
        Optional<List<Album>> albumList = albumRepository.findByClassroomAndKidIsNullOrderByAlbumDateDesc(classroom);
        return albumList.map(this::makeAlbumResponseDtoList).orElse(null);
    }

    public List<AlbumResponseDto> getKidAlbumList (Long kidId){
        Optional<Kid> kidResult = kidRepository.findById(kidId);
        if(kidResult.isEmpty()) throw new UserException(ExceptionCode.KID_NOT_FOUND);
        Kid kid = kidResult.get();

        Optional<List<Album>> albumList = albumRepository.findByKid(kid);
        return albumList.map(this::makeAlbumResponseDtoList).orElse(null);
    }

    public List<AlbumResponseDto> getKidAlbum(Long kidId, LocalDate date){
        Optional<Kid> kidResult = kidRepository.findById(kidId);
        if(kidResult.isEmpty()) throw new UserException(ExceptionCode.KID_NOT_FOUND);
        Kid kid = kidResult.get();

        Optional<List<Album>> albumList = albumRepository.findByKidAndAlbumDate(kid,date);
        return albumList.map(this::makeAlbumResponseDtoList).orElse(null);
    }

    public List<AlbumResponseDto> getTeacherClassAlbum(LocalDate date){
        User user = authenticationService.getUserByAuthentication();
        Optional<List<Album>> albumResult = albumRepository.findByClassroomAndAlbumDateAndKidIsNull(user.getClassroom(),date);
        return albumResult.map(this::makeAlbumResponseDtoList).orElse(null);

    }

    // 선생
    public List<KidAlbumResponseDto> getKidsCurrentAlbum (){
        User user = authenticationService.getUserByAuthentication();
        Classroom classroom = user.getClassroom();
        List<Kid> kids = kidRepository.findAllByClassroom(classroom);
        List<KidAlbumResponseDto> albumList = new ArrayList<>();
        for(Kid kid : kids){
            Optional<Album> album = albumRepository.findFirstByKidOrderByAlbumDateDesc(kid);
            KidAlbumResponseDto kidAlbumResponseDto = KidAlbumResponseDto.builder()
                    .kidId(kid.getId())
                    .kidName(kid.getNickname())
                    .albumResponseDto(album.map(this::makeAlbumResponseDto).orElse(null))
                    .build();
            albumList.add(kidAlbumResponseDto);
        }

        return albumList;
    }


    public List<AlbumResponseDto> getClassAlbum(Long kidId, LocalDate date){
        Optional<Kid> kidResult = kidRepository.findById(kidId);
        if(kidResult.isEmpty()) throw new UserException(ExceptionCode.KID_NOT_FOUND);
        Kid kid = kidResult.get();

        Classroom classroom = kid.getClassroom();
        Optional<List<Album>> albumList = albumRepository.findByClassroomAndAlbumDateAndKidIsNull(classroom, date);
        return albumList.map(this::makeAlbumResponseDtoList).orElse(null);
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
