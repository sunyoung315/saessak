package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Parent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {
    @EntityGraph(attributePaths = {"parent"})
    List<Kid> findAllByClassroomAndParentIsNotNull(Classroom classroom);
    List<Kid> findAllByClassroom(Classroom classroom);

    List<Kid> findAllByParent(Parent parent);
}
