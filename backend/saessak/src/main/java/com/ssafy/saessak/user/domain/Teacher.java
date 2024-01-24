package com.ssafy.saessak.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "teacher_email", nullable = false, unique = true)
    private String teacherEmail;

    @Column(name = "teacher_device")
    private String teacherDevice;

    @Column(name = "teacher_profile")
    private String teacherProfile;

    @Column(name = "teacher_alarm", nullable = false)
    private boolean teacherAlarm;

    @Column(name = "teacher_alarm_timestamp")
    private LocalDateTime teacherAlarmTimestamp;

    public void setToken(String token){
        this.teacherDevice = token;
    }

    public void setAlarm() {
        if(!this.teacherAlarm) {
            this.teacherAlarm = true;
        } else {
            this.teacherAlarm = false;
        }
    }

}
