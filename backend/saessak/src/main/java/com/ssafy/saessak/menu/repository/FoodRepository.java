package com.ssafy.saessak.menu.repository;

import com.ssafy.saessak.menu.domain.Food;
import com.ssafy.saessak.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByMenu(Menu menu);
}
