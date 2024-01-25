package com.ssafy.saessak.attendance.repository;

import com.ssafy.saessak.attendance.domain.Attendance;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByKidAndAttendanceDate(Kid kid, LocalDate localDate);

    List<Attendance> findAllByAttendanceDateBetweenAndKid(LocalDate startDate, LocalDate endDate, Kid kid);

}
