package com.ssafy.saessak.notice.repository;

import com.ssafy.saessak.notice.domain.Notice;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.classroom = :classroom AND NOT EXISTS (SELECT f FROM Fix f WHERE f.notice = n AND f.user = :user)")
    Page<Notice> findNoticesNotFixedByUser(@Param("classroom") Classroom classroom, @Param("user") User user, Pageable pageable);

    Page<Notice> findAllByClassroom(Classroom classroom, Pageable pageable);
    long countByClassroom(Classroom classroom);
}
