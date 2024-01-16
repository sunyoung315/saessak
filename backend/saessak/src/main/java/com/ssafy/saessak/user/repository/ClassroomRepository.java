package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
