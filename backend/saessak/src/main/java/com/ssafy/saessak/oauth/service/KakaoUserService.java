package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.RegistRequestDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.TeacherRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoUserService {

    private final UserRepository userRepository;
    private final ParentService parentService;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;

    public boolean checkUser(KakaoUserResponse kakaoUserResponse) {
        String userEmail = kakaoUserResponse.kakaoAccount().email();
        String userName = kakaoUserResponse.kakaoAccount().profile().nickname();
        return userRepository.findByEmailAndNickname(userEmail, userName).isPresent();
    }

    public Optional<Parent> isParent(KakaoUserResponse kakaoUserResponse) {
        return parentRepository.findByEmailAndNickname(kakaoUserResponse.kakaoAccount().email(),
                kakaoUserResponse.kakaoAccount().profile().nickname());
    }

    public Optional<Teacher> isTeacher(KakaoUserResponse kakaoUserResponse) {
        return teacherRepository.findByEmailAndNickname(kakaoUserResponse.kakaoAccount().email(),
                kakaoUserResponse.kakaoAccount().profile().nickname());
    }

//    public LoginSuccessResponseDto login(KakaoUserResponse kakaoUserResponse) {
//        String userEmail = kakaoUserResponse.kakaoAccount().email();
//        String userName = kakaoUserResponse.kakaoAccount().profile().nickname();
//        Long userId = 0L;
//        userId = parentService.isExistingParent(userEmail, userName);
//        if(userId > 0) {
//            return parentService.getTokenByUserId(userId);
//        }
//        userId = teacherService.isExistingTeacher(userEmail, userName);
//        if(userId > 0) {
//            return teacherService.getTokenByUserId(userId);
//        }
//        return null;
//    }

    public Long regist(String code, KakaoUserResponse kakaoUserResponse) {
        User user = User.builder()
                .email(kakaoUserResponse.kakaoAccount().email())
                .nickname(kakaoUserResponse.kakaoAccount().profile().nickname())
                .profile(kakaoUserResponse.kakaoAccount().profile().profileImageUrl())
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public LoginSuccessResponseDto join(RegistRequestDto registRequestDto) {
        // Base64 복호화
        String registCode = new String(Base64.getDecoder().decode(registRequestDto.getRegistCode()), StandardCharsets.UTF_8);
        if(registCode.substring(registCode.length()-7, registCode.length()).equals("daycare")) {
            Teacher teacher = Teacher.builder()
                    .id(registRequestDto.getUserId())
                    .build();
            teacherRepository.save(teacher);
            User user = userRepository.findById(teacher.getId()).get();
            return teacherService.getTokenByUserId(teacherService.getIdByEmailAndName(user.getEmail(), user.getNickname()));
        }
        if(registCode.substring(registCode.length()-3, registCode.length()).equals("kid")) {
            Parent parent = (Parent) Parent.builder()
                    .id(registRequestDto.getUserId())
                    .build();
            parentRepository.save(parent);
            User user = userRepository.findById(parent.getId()).get();
            return parentService.getTokenByUserId(parentService.getIdByEmailAndName(user.getEmail(), user.getNickname()));
        }
        return null;
    }

}
