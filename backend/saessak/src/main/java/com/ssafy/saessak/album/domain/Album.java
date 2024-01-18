package com.ssafy.saessak.album.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Kid;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increase ment
    private Long AlbumId;

    @Column
    private String albumTitle;

    @Column
    @Temporal(TemporalType.DATE)
    private Date albumDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @JsonIgnore
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final List<File> fileList = new ArrayList<>();

    public void addFile(File file) {
        fileList.add(file);
    }

}
