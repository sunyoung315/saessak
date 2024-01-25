package com.ssafy.saessak.menu.domain;

import com.ssafy.saessak.user.domain.Daycare;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daycare_id")
    private Daycare daycare;

    @Column(name = "menu_date")
    private LocalDate menuDate;

    @Column(name = "menu_type")
    private String menuType;

    @Column(name = "menu_path")
    private String menuPath; // s3 url 예정

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Food> foodList;

}
