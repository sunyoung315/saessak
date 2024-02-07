package com.ssafy.saessak.alarm.repository;

import com.ssafy.saessak.alarm.domain.Alarm;
import com.ssafy.saessak.user.domain.Kid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findByKidAndAlarmTypeIn(Kid kid, List<String> alarmTypeList);
    List<Alarm> findByAlarmTypeIn(List<String> alarmTypeList);
}
