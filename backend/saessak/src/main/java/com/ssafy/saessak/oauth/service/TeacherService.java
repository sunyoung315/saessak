package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.fcm.service.FcmService;
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
    private final FcmService fcmService;

    public Optional<Teacher> isTeacher(KakaoUserResponse kakaoUserResponse) {
        return teacherRepository.findByEmailAndNickname(kakaoUserResponse.kakaoAccount().email(),
                kakaoUserResponse.kakaoAccount().profile().nickname());
    }

    @Transactional
    public Long registTeacher(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionCode.USER_NOT_FOUND));

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
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new UserException(ExceptionCode.CLASSROOM_NOT_FOUND));
        Teacher teacher = teacherRepository.findById(teacherId)
                        .orElseThrow(() -> new UserException(ExceptionCode.TEACHER_NOT_FOUND));
        teacher.mapping_classroom(classroom);
    }

    public LoginTeacherResponseDto login(LoginSuccessResponseDto loginSuccessResponseDto) {
        Teacher teacher = teacherRepository.findById(loginSuccessResponseDto.getUserId())
                .orElseThrow(() -> new UserException(ExceptionCode.TEACHER_NOT_FOUND));

        String userToken = fcmService.getUserToken(teacher.getId());
        System.out.println(userToken);
        Boolean isAlarm = userToken == null ? false : true;

        LoginTeacherResponseDto loginTeacherResponseDto = LoginTeacherResponseDto.builder()
                .teacherName(teacher.getNickname())
                .isTeacher(true)
                .isAlarm(isAlarm)
                .teacherProfile(teacher.getProfile())
                .accessToken(loginSuccessResponseDto.getAccessToken())
                .refreshToken(loginSuccessResponseDto.getRefreshToken())
                .build();

        return loginTeacherResponseDto;
    }
}
