package com.ssafy.saessak.notice.domain;

import com.ssafy.saessak.menu.domain.Menu;
import com.ssafy.saessak.user.domain.Classroom;
import com.ssafy.saessak.user.domain.Daycare;
import com.ssafy.saessak.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name="notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content", length = 1000)
    private String noticeContent;

    @Column(name = "notice_time")
    private LocalDate noticeTime;

    @Column(name = "notice_file")
    private String noticeFile;

    @Column(name = "notice_file_name")
    private String noticeFileName;

}
