package com.ssafy.saessak.album.repository;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    List<Album> findByClassroom(Classroom classroom);
    List<Album> findByKid(Kid kid);
    Album findByKidAndAlbumDate(Kid kid, Date date);
    Album findByClassroomAndAlbumDate(Classroom classroom, Date date);


}
