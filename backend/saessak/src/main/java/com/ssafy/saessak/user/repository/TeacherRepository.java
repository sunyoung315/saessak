package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByClassroom(Classroom classroom);

    Optional<Teacher> findByEmailAndNickname(String teacherEmail, String teacherName);

}
