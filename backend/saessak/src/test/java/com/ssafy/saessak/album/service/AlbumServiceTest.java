package com.ssafy.saessak.album.service;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.domain.File;
import com.ssafy.saessak.album.repository.AlbumRepository;
import com.ssafy.saessak.album.repository.FileRepository;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.repository.ClassroomRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = AlbumService.class)
class AlbumServiceTest {

    @Autowired
    AlbumService albumService;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    FileRepository fileRepository;



    private Long albumId = 0L;
    @BeforeEach
    public void setup() {
        Classroom classroom = Classroom.builder()
                .classroomId(1L)
                .build();
        //엘범 제작
        Album album = Album.builder()
                .classroom(classroom)
                .albumTitle("파일 추가 버전")
                .albumDate(Calendar.getInstance().getTime()).build();

        //그 엘범에 파일 넣기 (3~4개쯤? )
        for(int i = 0 ; i < 10; i++){
            File file = File.builder()
                    .album(album)
                    .fileName("file_no_" + i)
                    .filePath("file_path_"+ i)
                    .build();
            album.addFile(file);
            fileRepository.save(file);
        }
        albumRepository.save(album);
        albumId = album.getAlbumId();
    }

    @Test
    public void fetchTest() {
        Album album = albumRepository.findById(albumId).get();


        assertEquals(album.getFileList().size(),10);
    }

    @Test
    public void fetchFiles() {
        List<File> files = fileRepository.findAll();

        assertEquals(files.size(),10);
    }

}