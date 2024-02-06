package com.ssafy.saessak.user.domain;

import com.ssafy.saessak.menu.domain.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="daycare")
public class Daycare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long daycareId;

    @Column(name = "daycare_name", nullable = false)
    private String daycareName;

    @OneToMany(mappedBy = "daycare", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Classroom> classroomList;

    @OneToMany(mappedBy = "daycare", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Menu> menuList;
}
