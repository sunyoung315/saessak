package com.ssafy.saessak.album.repository;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.user.domain.Classroom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class AlbumRepositoryTest {


    @Autowired
    AlbumRepository albumRepository;

    private Long albumId = 0L;
    @BeforeEach
    public void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024,12,12);
        Classroom classroom = Classroom.builder()
                .classroomId(1L)
                .build();
        //given
        Album album = Album.builder()
                .classroom(classroom)
                .albumDate(calendar.getTime())
                .albumTitle("엘범 입력")
                .build();

        //when
        Album saved = albumRepository.save(album);
        albumId = saved.getAlbumId();
    }

    @Test
    public void albumREpositoryTest() {
        assertNotNull(albumRepository);

    }

    @Test
    public void insertTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024,12,12);
        Classroom classroom = Classroom.builder()
                .classroomId(1L)
                .build();
        //given
        Album album = Album.builder()
                .classroom(classroom)
                .albumDate(calendar.getTime())
                .albumTitle("엘범 입력")
                .build();
        log.debug("before : {}\n",album);

        //when
        Album saved = albumRepository.save(album);

        log.debug("after : {}\n",album);
        //then
        assertEquals(album, saved);
    }

    @Test
    public void selectTest() {
        Optional<Album> select = albumRepository.findById(albumId);
        if(select.isPresent()){
            Album album = select.get();
            assertNotNull(album);
            assertEquals(album.getAlbumTitle(), "엘범 입력");
        }
    }

    @Test
    public void deleteTest() {
       Album album =  albumRepository.findById(albumId).get();

       albumRepository.delete(album);


       assertEquals(albumRepository.findAll().size() , 0);
    }

    @Test
    public void updateTest() {
        Album album = albumRepository.findById(albumId).get();

        Album updated = Album.builder()
                .AlbumId(album.getAlbumId())
                .albumTitle("replaced")
                .albumDate(album.getAlbumDate())
                .build();

        albumRepository.save(updated);
        //

        Album result = albumRepository.findById(albumId).get();
//        assertEquals(result, updated);
        assertEquals(albumRepository.findAll().size(), 1);

    }

    @Test
    public void findByClassroomId() {
        List<Album> albums = albumRepository.findByClassroom(Classroom.builder().classroomId(1L).build());
        assertEquals(albums.size(), 1);
    }


}