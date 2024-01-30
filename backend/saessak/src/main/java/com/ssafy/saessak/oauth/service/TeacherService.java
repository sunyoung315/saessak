package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.LoginTeacherResponseDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.user.domain.Classroom;

import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.ClassroomRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    public Optional<Teacher> isTeacher(KakaoUserResponse kakaoUserResponse) {
        return teacherRepository.findByEmailAndNickname(kakaoUserResponse.kakaoAccount().email(),
                kakaoUserResponse.kakaoAccount().profile().nickname());
    }

    @Transactional
    public Long registTeacher(Long userId) {
        User user = userRepository.findById(userId).get();
        Teacher teacher = Teacher.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .build();
        userRepository.delete(user);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return savedTeacher.getId();
    }

    public void mapping(Long teacherId, Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).get();
        Teacher teacher = teacherRepository.findById(teacherId).get();
        teacher.mapping_classroom(classroom);
    }

    public LoginTeacherResponseDto login(LoginSuccessResponseDto loginSuccessResponseDto) {
        Teacher teacher = teacherRepository.findById(loginSuccessResponseDto.getUserId()).get();
        LoginTeacherResponseDto loginTeacherResponseDto = LoginTeacherResponseDto.builder()
                .teacherName(teacher.getNickname())
                .isTeacher(true)
                .accessToken(loginSuccessResponseDto.getAccessToken())
                .refreshToken(loginSuccessResponseDto.getRefreshToken())
                .build();

        return loginTeacherResponseDto;
    }
}
