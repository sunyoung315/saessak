package com.ssafy.saessak.menu.repository;

import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.user.domain.Daycare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByDaycareAndMenuDateAndMenuType (Daycare daycare, LocalDate menuDate, String menuType);

    List<Menu> findByMenuDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Menu> findByMenuDateAndMenuType(LocalDate now, String menuType);
}
