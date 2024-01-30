package com.ssafy.saessak.user.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String nickname;
    private String email;
    private String profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="classroom_id")
    private Classroom classroom;

    public void mapping_classroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void uploadProfile(String profile) {
        this.profile = profile;
    }
}
