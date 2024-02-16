package com.ssafy.saessak.attendance.domain;

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
@Table(name="attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "attendance_in_time")
    private LocalTime attendanceInTime;

    @Column(name = "attendance_out_time")
    private LocalTime attendanceOutTime;

    public Attendance outTime(){
        this.attendanceOutTime = LocalTime.now();
        return this;
    }

}
