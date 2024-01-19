package com.ssafy.saessak.album.repository;

import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.album.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
//    Optional<File> findByAlbum(Album album);
}
