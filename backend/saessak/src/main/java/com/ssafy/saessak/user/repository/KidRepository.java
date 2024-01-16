package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {
}
