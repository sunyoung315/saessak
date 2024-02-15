package com.ssafy.saessak.notice.repository;

import com.ssafy.saessak.notice.domain.Fix;
import com.ssafy.saessak.notice.domain.Notice;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FixRepository extends JpaRepository<Fix, Long> {
    Optional<Fix> findByNoticeAndUser(Notice n, User u);
    List<Fix> findAllByUser(User u);

    long countByUser(User u);
}
