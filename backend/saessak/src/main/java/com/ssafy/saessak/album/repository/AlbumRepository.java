package com.ssafy.saessak.album.repository;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    // 학급 전체 엘범
    Optional<List<Album>> findByClassroomAndKidIsNull(Classroom classroom);
    // 아이 전체 엘범
    Optional<List<Album>> findByKid(Kid kid);
    // 학급 날짜 엘범
    Optional<List<Album>> findByClassroomAndAlbumDateAndKidIsNull(Classroom classroom, LocalDate date);
    // 아이 날짜 엘범
    Optional<List<Album>> findByKidAndAlbumDate(Kid kid, LocalDate date);
    
    Optional<Album> findFirstByKidOrderByAlbumDateDesc(Kid kid);

    Optional<Album> findFirstByClassroomAndKidIsNull(Classroom classroom);
}
