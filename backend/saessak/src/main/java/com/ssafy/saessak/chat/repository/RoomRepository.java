package com.ssafy.saessak.chat.repository;

import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByKid(Kid kid);
    List<Room> findAllByKidAndTeacher(Kid kid, Teacher teacher);
}
