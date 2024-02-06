package com.ssafy.saessak.growth.repository;
import com.ssafy.saessak.growth.domain.KidWeight;
import com.ssafy.saessak.user.domain.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KidWeightRepository extends JpaRepository<KidWeight,Long> {
    Optional<List<KidWeight>> findAllByGenderAndNumberOfMonthBetween(Gender gender, Integer start, Integer end);
}
