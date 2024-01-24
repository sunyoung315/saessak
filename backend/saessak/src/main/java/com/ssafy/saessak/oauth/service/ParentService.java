package com.ssafy.saessak.oauth.service;

import com.ssafy.saessak.oauth.authentication.UserAuthentication;
import com.ssafy.saessak.oauth.dto.AccessTokenGetSuccess;
import com.ssafy.saessak.oauth.dto.KakaoUserResponse;
import com.ssafy.saessak.oauth.dto.LoginSuccessResponse;
import com.ssafy.saessak.oauth.jwt.JwtTokenProvider;
import com.ssafy.saessak.user.domain.Parent;
import com.ssafy.saessak.user.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ParentRepository parentRepository;
    private final RefreshTokenService refreshTokenService;

    public Long createUser(final KakaoUserResponse userResponse) {
        Parent parent = Parent.builder()
                .parentEmail(userResponse.kakaoAccount().profile().accountEmail())
                .parentName(userResponse.kakaoAccount().profile().nickname())
                .parentProfile(userResponse.kakaoAccount().profile().profileImageUrl())
                .build();

        return parentRepository.save(parent).getParentId();
    }

    public Long getIdByEmail(String email, String name) {
        Optional<Parent> parent = parentRepository.findByParentEmailAndParentName(email, name);
        if(parent.isPresent()) {
            return parent.get().getParentId();
        }
        return 0L;
    }

//    public Long getIdBySocialId(final Long socialId ) {
//        Optional<Parent> parent = parentRepository.findBySocialId(socialId);
//        if(parent.isPresent()) {
//            return parent.get().getParentId();
//        }
//        return 0L;
//    }

    public AccessTokenGetSuccess refreshToken( final String refreshToken ) {
        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
//        if (!userId.equals(refreshTokenService.findIdByRefreshToken(refreshToken))) {
//            throw new UnAuthorizedException(ErrorMessage.TOKEN_INCORRECT_ERROR);
//        }
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        return new AccessTokenGetSuccess(
                jwtTokenProvider.issueAccessToken(userAuthentication)
        );
    }

    public boolean isExistingUser(String email, String name) {
        return parentRepository.findByParentEmailAndParentName(email, name).isPresent();
    }

//    public boolean isExistingUser( final Long socialId ) {
//        return parentRepository.findBySocialId(socialId).isPresent();
//    }

    public LoginSuccessResponse getTokenByUserId(final Long id ) {
        UserAuthentication userAuthentication = new UserAuthentication(id, null, null);
        String refreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);
//        refreshTokenService.saveRefreshToken(id, refreshToken);
        return LoginSuccessResponse.of(
                jwtTokenProvider.issueAccessToken(userAuthentication),
                refreshToken
        );
    }

    @Transactional
    public void deleteUser( final Long id ) {
        Optional<Parent> parent = parentRepository.findById(id);
        if(parent.isPresent()) {
            parentRepository.deleteById(parent.get().getParentId());
        }
    }
}
