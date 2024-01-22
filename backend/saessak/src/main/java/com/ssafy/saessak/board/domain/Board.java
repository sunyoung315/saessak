package com.ssafy.saessak.board.domain;


import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column
    @Temporal(TemporalType.DATE)
    private Date boardDate;

    @Column
    private String boardContent;

    @Column
    private String boardTemperature;

    @Column(name = "board_sleeptime")
    private Integer boardSleepTime;

    @Column
    private String boardPoopStatus;

    @Column
    private Float boardTall;

    @Column
    private Float boardWeight;

    @Column
    private String boardPath;

    // join
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
