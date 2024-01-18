package com.ssafy.saessak.album.repository;

import com.ssafy.saessak.album.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
