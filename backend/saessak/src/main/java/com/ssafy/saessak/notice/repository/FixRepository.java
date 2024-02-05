package com.ssafy.saessak.notice.repository;

import com.ssafy.saessak.notice.domain.Fix;
import com.ssafy.saessak.notice.domain.Notice;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FixRepository extends JpaRepository<Fix, Long> {
    Optional<Fix> findByNoticeAndKid(Notice n, Kid kid);
    Page<Fix> findAllByKid(Kid kid, Pageable pageable);
    List<Fix> findAllByKid(Kid kid);
}
