package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Daycare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaycareRepository extends JpaRepository<Daycare, Long> {
}
