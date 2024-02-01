package com.ssafy.saessak.growth.repository;

import com.ssafy.saessak.growth.domain.KidTall;
import com.ssafy.saessak.user.domain.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KidTallRepository extends JpaRepository<KidTall,Long> {
    Optional<List<KidTall>> findAllByGenderAndNumberOfMonthBetween(Gender gender, Integer start, Integer end);

}
