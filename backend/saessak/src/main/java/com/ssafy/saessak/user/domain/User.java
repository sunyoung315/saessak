package com.ssafy.saessak.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
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
}
