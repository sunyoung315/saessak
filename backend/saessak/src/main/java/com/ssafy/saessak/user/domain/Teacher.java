package com.ssafy.saessak.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="teacher")
public class Teacher extends User {

    public void mapping_classroom(Classroom classroom) {
        super.mapping_classroom(classroom);
    }

}
