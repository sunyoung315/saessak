package com.ssafy.saessak.growth.domain;

import com.ssafy.saessak.user.domain.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kid_weight")
public class KidWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer weightId;
    @Enumerated(value = EnumType.STRING)
    @Column
    private Gender gender;
    @Column
    private Integer numberOfMonth;
    @Column
    private Float top5;
    @Column
    private Float top50;
    @Column
    private Float top95;
}
