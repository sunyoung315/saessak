package com.ssafy.saessak.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.saessak.album.domain.Album;
import com.ssafy.saessak.board.domain.Board;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="classroom")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    @Column(name = "classroom_name", nullable = false)
    private String classroomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="daycare_id")
    private Daycare daycare;

    @JsonIgnore
    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Album> albumList;

    @JsonIgnore
    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Board> boardList;

}
