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
@Table(name = "kid_tall")
public class KidTall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tallId;
    @Column
    private Integer numberOfMonth;
    @Enumerated(value = EnumType.STRING)
    @Column
    private Gender gender;
    @Column
    private Float top3;
    @Column
    private Float top50;
    @Column
    private Float top97;

}
