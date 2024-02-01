package com.ssafy.saessak.oauth.jwt.filter;

import com.ssafy.saessak.exception.code.ExceptionCode;
import com.ssafy.saessak.oauth.authentication.UserAuthentication;
import com.ssafy.saessak.oauth.jwt.JwtTokenProvider;
import com.ssafy.saessak.oauth.jwt.JwtValidationType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ssafy.saessak.oauth.jwt.JwtValidationType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    private String[] AUTH_WHITELIST = {
            "/api/oauth/",
            "/api/test/",
            "/api/v1/urls/",
            "/api/v2/urls/",
            "/swagger-ui/",
            "/v3/api-docs",
            "/swagger-resources/",
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        String token = getJwtFromRequest(request);

        for(String url : AUTH_WHITELIST) {
            if(requestUrl.startsWith(url)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (token != null && jwtTokenProvider.validateToken(token) == VALID_JWT_TOKEN) {
            Long userId = jwtTokenProvider.getUserFromJwt(token);

            UserAuthentication authentication = new UserAuthentication(userId.toString(), null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } else {
            JwtValidationType type = jwtTokenProvider.validateToken(token);
            if(type.equals(INVALID_JWT_TOKEN)) sendError(ExceptionCode.INVALID_JWT_ACCESS_TOKEN, response);
            if(type.equals(EXPIRED_JWT_TOKEN)) sendError(ExceptionCode.ACCESS_TOKEN_EXPIRED, response);
            if(type.equals(UNSUPPORTED_JWT_TOKEN)) sendError(ExceptionCode.UNSUPPORTED_JWT_ACCESS_TOKEN, response);
            if(type.equals(EMPTY_JWT_TOKEN)) sendError(ExceptionCode.EMPTY_JWT_TOKEN, response);
            return;
        }

    }

    private void sendError(ExceptionCode exceptionCode, HttpServletResponse response) throws IOException {
        response.setStatus(exceptionCode.getStatus());
        response.setHeader("Content-Type", "application/xml");
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");

        String code = exceptionCode.getCode();
        String message = exceptionCode.getMessage();
        String jsonErrorResponse = String.format("{\"code\": \"%s\", \"message\": \"%s\"}", code, message);
        response.getWriter().write(jsonErrorResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE.length());
        }
        return null;
    }
}
