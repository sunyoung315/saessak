package com.ssafy.saessak.user.domain;

import com.ssafy.saessak.todo.domain.Todo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Todo> todoList;
    public void mapping_classroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void uploadProfile(String profile) {
        this.profile = profile;
    }
}
