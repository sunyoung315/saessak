package com.ssafy.saessak.user.domain;


import com.ssafy.saessak.attendance.domain.Attendance;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="parent")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @Column(name = "parent_name", nullable = false)
    private String parentName;

    @Column(name = "parent_email", nullable = false, unique = true)
    private String parentEmail;

    @Column(name = "parent_password", nullable = false)
    private String parentPassword;

    @Column(name = "parent_device")
    private String parentDevice;

    @Column(name = "parent_alarm", nullable = false)
    private Boolean parentAlarm;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final List<Kid> kidList = new ArrayList<>();

    public void setToken(String token){
        this.parentDevice = token;
    }

    public void setAlarm() {
        if(!this.parentAlarm) {
            this.parentAlarm = true;
        } else {
            this.parentAlarm = false;
        }
    }

}

