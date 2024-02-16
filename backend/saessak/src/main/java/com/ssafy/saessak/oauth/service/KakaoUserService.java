package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.exception.model.UserException;
import com.ssafy.saessak.oauth.authentication.UserAuthentication;
import com.ssafy.saessak.oauth.dto.AccessTokenGetSuccess;
import com.ssafy.saessak.oauth.dto.LoginSuccessResponseDto;
import com.ssafy.saessak.oauth.dto.RegistResponseDto;
import com.ssafy.saessak.oauth.dto.kakao.KakaoUserResponse;
import com.ssafy.saessak.jwt.JwtTokenProvider;
import com.ssafy.saessak.jwt.refreshToken.service.RefreshTokenService;
import com.ssafy.saessak.user.domain.Kid;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.domain.User;
import com.ssafy.saessak.user.repository.KidRepository;
import com.ssafy.saessak.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoUserService {

    private final UserRepository userRepository;
    private final KidRepository kidRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean checkUser(KakaoUserResponse kakaoUserResponse) {
        String userEmail = kakaoUserResponse.kakaoAccount().email();
        String userName = kakaoUserResponse.kakaoAccount().profile().nickname();
        return userRepository.findByEmailAndNickname(userEmail, userName).isPresent();
    }

    public LoginSuccessResponseDto getTokenByUserId (Long userId) {
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

    public RegistResponseDto registUser(KakaoUserResponse kakaoUserResponse) {
        User user = User.builder()
                .email(kakaoUserResponse.kakaoAccount().email())
                .nickname(kakaoUserResponse.kakaoAccount().profile().nickname())
                .profile(kakaoUserResponse.kakaoAccount().profile().profileImageUrl())
                .build();

        User savedUser = userRepository.save(user);
        RegistResponseDto registResponseDto = RegistResponseDto.builder()
                .userId(savedUser.getId())
                .accessToken("null")
                .build();
        return registResponseDto;
    }

    public AccessTokenGetSuccess refreshToken(final String refreshToken ) {
        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
//        if (!userId.equals(refreshTokenService.findIdByRefreshToken(refreshToken))) {
//            throw new UnAuthorizedException(ExceptionCode.REFRESH_TOKEN_USER_MISMATCH);
//        }
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        return new AccessTokenGetSuccess(
                jwtTokenProvider.issueAccessToken(userAuthentication)
        );
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ExceptionCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void deleteAnonymousUser() {
        List<User> userList = userRepository.findAllByClassroomIsNull();
        for(User user : userList) {
            if(user instanceof Parent) {
                List<Kid> kidList = kidRepository.findAllByParent((Parent) user);
                if(kidList.size() == 0) {
                    userRepository.delete(user);
                }
            } else {
                userRepository.delete(user);
            }
        }
    }

}
