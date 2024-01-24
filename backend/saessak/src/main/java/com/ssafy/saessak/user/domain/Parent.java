package com.ssafy.saessak.user.domain;


import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;
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

//    @Column(name = "parent_social_id")
//    private Long parentSocialId;

    @Column(name = "parent_name", nullable = false)
    private String parentName;

    @Column(name = "parent_email", nullable = false, unique = true)
    private String parentEmail;

    @Column(name = "parent_device")
    private String parentDevice;

    @Column(name = "parent_profile")
    private String parentProfile;

    @Column(name = "parent_alarm")
    private Boolean parentAlarm;

    @Column(name = "parent_alarm_timestamp")
    private LocalDateTime parentAlarmTimestamp;

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

