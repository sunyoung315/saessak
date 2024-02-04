package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Daycare;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByDaycare(Daycare daycare);

}
