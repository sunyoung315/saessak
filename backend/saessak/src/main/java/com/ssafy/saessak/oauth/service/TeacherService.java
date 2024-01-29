package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.authentication.UserAuthentication;
import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.LoginTeacherResponseDto;
import com.ssafy.saessak.oauth.jwt.JwtTokenProvider;
import com.ssafy.saessak.oauth.token.service.RefreshTokenService;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    public Long isExistingTeacher(String email, String name) {
        Optional<Teacher> teacher = teacherRepository.findByEmailAndNickname(email, name);
        if(teacher.isPresent()) {
            return teacher.get().getId();
        }
        return 0L;
    }

    public Long getIdByEmailAndName(String email, String name) {
        Teacher teacher = teacherRepository.findByEmailAndNickname(email, name).get();
        return teacher.getId();
    }

    public LoginSuccessResponseDto getTokenByUserId (final Long userId) {
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        String refreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);
        refreshTokenService.saveRefreshToken(userId, refreshToken);
        return LoginSuccessResponseDto.builder()
                .userId(userId)
                .accessToken(jwtTokenProvider.issueAccessToken(userAuthentication))
                .refreshToken(refreshToken)
                .isTeacher(true)
                .build();
    }

    public LoginTeacherResponseDto login(LoginSuccessResponseDto loginSuccessResponseDto) {
        Teacher teacher = teacherRepository.findById(loginSuccessResponseDto.getUserId()).get();
        LoginTeacherResponseDto loginTeacherResponseDto = LoginTeacherResponseDto.builder()
                .teacherId(teacher.getId())
                .teacherName(teacher.getNickname())
                .isTeacher(true)
                .classroomId(teacher.getClassroom().getClassroomId())
                .accessToken(loginSuccessResponseDto.getAccessToken())
                .refreshToken(loginSuccessResponseDto.getRefreshToken())
                .build();

        return loginTeacherResponseDto;
    }
}
