package com.ssafy.saessak.chat.repository;

import com.ssafy.saessak.chat.domain.Chat;
import com.ssafy.saessak.chat.domain.Room;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Teacher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findFirstByRoomOrderByChatTimeDesc(Room room);

    Slice<Chat> findAllByChatTimeBeforeAndRoomOrderByChatTimeDesc(String lastCursor, Room room, PageRequest of);

    List<Chat> findAllByRoom(Room room);
}
