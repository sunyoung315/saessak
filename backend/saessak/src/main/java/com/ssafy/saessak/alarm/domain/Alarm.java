package com.ssafy.saessak.alarm.domain;

import com.ssafy.saessak.user.domain.Kid;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name="alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @Column(name = "alarm_type")
    private String alarmType;

    @Column(name = "alarm_date")
    private LocalDate alarmDate;

    @Column(name = "alarm_content")
    private LocalTime alarmContent;

}
