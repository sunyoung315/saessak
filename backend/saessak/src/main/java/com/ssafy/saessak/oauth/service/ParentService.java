package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.fcm.service.FcmService;
import com.ssafy.saessak.oauth.dto.KidResponseDto;
import com.ssafy.saessak.oauth.dto.LoginParentResponseDto;
import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.user.domain.*;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final KidRepository kidRepository;
    private final FcmService fcmService;

    public Optional<Parent> isParent(KakaoUserResponse kakaoUserResponse) {
        return parentRepository.findByEmailAndNickname(kakaoUserResponse.kakaoAccount().email(),
                kakaoUserResponse.kakaoAccount().profile().nickname());
    }

    @Transactional
    public Long registParent(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionCode.USER_NOT_FOUND));

        Parent parent = Parent.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .build();
        userRepository.delete(user);
        Parent savedParent = parentRepository.save(parent);
        return savedParent.getId();
    }

    public void mapping(Long parentId, Long kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new UserException(ExceptionCode.KID_NOT_FOUND));
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new UserException(ExceptionCode.PARENT_NOT_FOUND));
        List<Kid> kidList = parent.getKidList();
        if(kidList == null) kidList = new ArrayList<>();

        kidList.add(kid);
        kid.mapping_parent(parent);
    }

    public LoginParentResponseDto login(LoginSuccessResponseDto loginSuccessResponseDto) {
        List<KidResponseDto> kidResponseDtoList = new ArrayList<>();
        Parent parent = parentRepository.findById(loginSuccessResponseDto.getUserId())
                .orElseThrow(() -> new UserException(ExceptionCode.PARENT_NOT_FOUND));
        List<Kid> kidList = kidRepository.findAllByParent(parent);
        for(Kid kid : kidList) {
            KidResponseDto kidResponseDto = KidResponseDto.builder()
                    .kidId(kid.getId())
                    .kidName(kid.getNickname())
                    .kidProfile(kid.getProfile())
                    .build();
            kidResponseDtoList.add(kidResponseDto);
        }

        String userToken = fcmService.getUserToken(parent.getId());
        Boolean isAlarm = userToken.equals(null) ? false : true;
        LoginParentResponseDto loginResponseDto = LoginParentResponseDto.builder()
                .isTeacher(false)
                .isAlarm(isAlarm)
                .accessToken(loginSuccessResponseDto.getAccessToken())
                .refreshToken(loginSuccessResponseDto.getRefreshToken())
                .kidList(kidResponseDtoList)
                .build();

        return loginResponseDto;
    }
}
