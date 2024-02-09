package com.ssafy.saessak.notice.repository;

import com.ssafy.saessak.notice.domain.Notice;
import com.ssafy.saessak.user.domain.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAllByClassroom(Classroom classroom, Pageable pageable);
    long countByClassroom(Classroom classroom);
}
