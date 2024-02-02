package com.ssafy.saessak.chat.repository;

import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.chat.domain.Visit;
import com.ssafy.saessak.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit,Long> {
    Visit findByUserAndRoom(User user, Room room);

}
