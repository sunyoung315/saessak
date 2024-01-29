package com.ssafy.saessak.user.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="parent")
public class Parent extends User {

    @Column(name = "parent_device")
    private String parentDevice;

    @Column(name = "parent_alarm")
    private Boolean parentAlarm;

    @Column(name = "parent_alarm_timestamp")
    private LocalDateTime parentAlarmTimestamp;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Kid> kidList;

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

