package com.ssafy.saessak.document.domain;

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
@Table(name="replacement")
public class Replacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replacementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @Column(name = "replacement_date")
    private String replacementDate;

    @Column(name = "replacement_time")
    private String replacementTime;

    @Column(name = "replacement_vehicle")
    private String replacementVehicle;

    @Column(name = "replacement_relationship")
    private String replacementRelationship;

    @Column(name = "replacement_number")
    private String replacementNumber;

    @Column(name = "replacement_name")
    private String replacementName;

    @Column(name = "replacement_signature")
    private String replacementSignature;

    @Column(name = "replacement_check")
    private boolean replacementCheck;

    @Column(name = "replacement_day")
    private LocalDate replacementDay;

    public void changeCheck() {
        this.replacementCheck = true;
    }
}
