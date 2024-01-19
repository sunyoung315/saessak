package com.ssafy.saessak.document.repository;

import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplacementRepository extends JpaRepository<Replacement, Long> {

    List<Replacement> findByKid(Kid kid);
}
