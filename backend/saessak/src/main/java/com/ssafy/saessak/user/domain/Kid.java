package com.ssafy.saessak.user.domain;


import com.ssafy.saessak.attendance.domain.Attendance;
import com.ssafy.saessak.document.domain.Replacement;
import com.ssafy.saessak.album.domain.Album;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="kid")
public class Kid extends User {

    @Column(name = "kid_name", nullable = false)
    private String kidName;

    @Column(name = "kid_birthday", nullable = false)
    private LocalDate kidBirthday;

    @Column(name = "kid_allergy")
    private String kidAllergy;

    @Column(name = "kid_profile")
    private String kidProfile; // s3 url 예정

    @Column(name = "kid_allergy_signature")
    private String kidAllergySignature;

    @Column(name = "kid_invite_link")
    private String kidInviteLink;

    @Column(name = "kid_allergy_check")
    private Boolean kidAllergyCheck; // 확인 여부

    @Column(name = "kid_allergy_date")
    private LocalDate kidAllergyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="classroom_id")
    private Classroom classroom;

    @OneToMany(mappedBy = "kid", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Attendance> attendanceList;

    @OneToMany(mappedBy = "kid", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Replacement> replacementList;

    @OneToMany(mappedBy = "kid")
    private List<Album> albums;

    public Kid updateParent(Parent parent){
        this.parent = parent;
        return this;
    }

    public void setAllergy(String kidAllergy, String kidAllergySignature, LocalDate now) {
        this.kidAllergy = kidAllergy;
        this.kidAllergySignature = kidAllergySignature;
        this.kidAllergyDate = now;
    }

    public void changeCheck() {
        this.kidAllergyCheck = true;
    }
}
