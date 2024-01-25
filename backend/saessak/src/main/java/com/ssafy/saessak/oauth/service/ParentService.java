package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.authentication.UserAuthentication;
import com.ssafy.saessak.oauth.dto.AccessTokenGetSuccess;
import com.ssafy.saessak.oauth.dto.KidResponseDto;
import com.ssafy.saessak.oauth.dto.LoginParentResponseDto;
import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.oauth.exception.UnAuthorizedException;
import com.ssafy.saessak.oauth.exception.message.ErrorMessage;
import com.ssafy.saessak.oauth.jwt.JwtTokenProvider;
import com.ssafy.saessak.oauth.token.service.RefreshTokenService;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    private final RefreshTokenService refreshTokenService;

    public Long createUser(final KakaoUserResponse userResponse) {
        Parent parent = Parent.builder()
                .parentEmail(userResponse.kakaoAccount().email())
                .parentName(userResponse.kakaoAccount().profile().nickname())
                .parentProfile(userResponse.kakaoAccount().profile().profileImageUrl())
                .parentAlarm(false)
                .build();

        return parentRepository.save(parent).getId();
    }

    public Long getIdByEmailAndName(String email, String name) {
        Parent parent = parentRepository.findByParentEmailAndParentName(email, name).get();
        return parent.getId();
    }

    public AccessTokenGetSuccess refreshToken( final String refreshToken ) {
        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
        if (!userId.equals(refreshTokenService.findIdByRefreshToken(refreshToken))) {
            throw new UnAuthorizedException(ErrorMessage.TOKEN_INCORRECT_ERROR);
        }
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        return new AccessTokenGetSuccess(
                jwtTokenProvider.issueAccessToken(userAuthentication)
        );
    }

    public boolean isExistingUser(String email, String name) {
        return parentRepository.findByParentEmailAndParentName(email, name).isPresent();
    }

    public LoginSuccessResponseDto getTokenByUserId (final Long userId) {
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        String refreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);
        refreshTokenService.saveRefreshToken(userId, refreshToken);
        return LoginSuccessResponseDto.builder()
                .userId(userId)
                .accessToken(jwtTokenProvider.issueAccessToken(userAuthentication))
                .refreshToken(refreshToken)
                .isTeacher(false)
                .build();
    }

    @Transactional
    public void deleteUser (final Long id) {
        Parent parent = parentRepository.findById(id).get();
        parentRepository.delete(parent);
    }

    public LoginParentResponseDto login(LoginSuccessResponseDto loginSuccessResponseDto) {
        List<KidResponseDto> kidResponseDtoList = new ArrayList<>();
        Parent parent = parentRepository.findById(loginSuccessResponseDto.getUserId()).get();
        List<Kid> kidList = kidRepository.findAllByParent(parent);
        for(Kid kid : kidList) {
            KidResponseDto kidResponseDto = KidResponseDto.builder()
                    .classroomId(kid.getClassroom().getClassroomId())
                    .parentId(kid.getParent().getId())
                    .kidId(kid.getId())
                    .kidName(kid.getKidName())
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
