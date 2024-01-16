package com.ssafy.saessak.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="kid")
public class Kid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kidId;

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

    public Kid updateParent(Parent parent){
        this.parent = parent;
        return this;
    }
}
