package com.ssafy.saessak.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="teacher")
public class Teacher extends User {

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "teacher_email")
    private String teacherEmail;

    @Column(name = "teacher_password")
    private String teacherPassword;

    @Column(name = "teacher_device")
    private String teacherDevice;

    @Column(name = "teacher_profile")
    private String teacherProfile;

    @Column(name = "teacher_alarm")
    private boolean teacherAlarm;

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
