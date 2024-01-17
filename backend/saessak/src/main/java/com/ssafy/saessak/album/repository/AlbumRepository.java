package com.ssafy.saessak.album.repository;

import com.ssafy.saessak.album.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {
}
