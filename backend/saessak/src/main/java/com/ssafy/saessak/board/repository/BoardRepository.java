package com.ssafy.saessak.board.repository;

import com.ssafy.saessak.board.domain.Board;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<List<Board>> findByKid(Kid kid);
    Optional<Board> findFirstByKidAndBoardDateOrderByBoardId(Kid kid, LocalDate date);
    Optional<List<Board>> findByKidAndBoardDateBetween(Kid kid, LocalDate start, LocalDate end);
    Optional<Board> findFirstByKidOrderByBoardDateDesc(Kid kid);
    Optional<List<Board>> findByKidAndBoardDateGreaterThanEqualAndBoardDateLessThanEqualOrderByBoardDate(Kid kid, LocalDate startDate, LocalDate endDate);
}
