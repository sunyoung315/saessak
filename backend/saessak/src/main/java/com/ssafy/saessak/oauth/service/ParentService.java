package com.ssafy.saessak.oauth.service;

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

    public Optional<Parent> isParent(KakaoUserResponse kakaoUserResponse) {
        return parentRepository.findByEmailAndNickname(kakaoUserResponse.kakaoAccount().email(),
                kakaoUserResponse.kakaoAccount().profile().nickname());
    }

    @Transactional
    public Long registParent(Long userId) {
        User user = userRepository.findById(userId).get();
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
        Kid kid = kidRepository.findById(kidId).get();
        Parent parent = parentRepository.findById(parentId).get();
        List<Kid> kidList = parent.getKidList();
        if(kidList == null) kidList = new ArrayList<>();
        kidList.add(kid);
        kid.mapping_parent(parent);
    }

    public LoginParentResponseDto login(LoginSuccessResponseDto loginSuccessResponseDto) {
        List<KidResponseDto> kidResponseDtoList = new ArrayList<>();
        Parent parent = parentRepository.findById(loginSuccessResponseDto.getUserId()).get();
        List<Kid> kidList = kidRepository.findAllByParent(parent);
        for(Kid kid : kidList) {
            KidResponseDto kidResponseDto = KidResponseDto.builder()
                    .kidId(kid.getId())
                    .kidName(kid.getNickname())
                    .build();
            kidResponseDtoList.add(kidResponseDto);
        }

        LoginParentResponseDto loginResponseDto = LoginParentResponseDto.builder()
                .isTeacher(false)
                .accessToken(loginSuccessResponseDto.getAccessToken())
                .refreshToken(loginSuccessResponseDto.getRefreshToken())
                .kidList(kidResponseDtoList)
                .build();

        return loginResponseDto;
    }
}
