package com.ssafy.saessak.board.repository;

import com.ssafy.saessak.board.domain.Board;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<List<Board>> findByKid(Kid kid);
    Optional<List<Board>> findByKidAndBoardDate(Kid kid, Date date);
}
