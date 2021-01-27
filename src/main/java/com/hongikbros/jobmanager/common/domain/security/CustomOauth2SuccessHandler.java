package com.hongikbros.jobmanager.common.domain.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongikbros.jobmanager.member.application.member.MemberResponse;
import com.hongikbros.jobmanager.member.application.member.SessionMember;
import com.hongikbros.jobmanager.member.application.oauth.CustomOAuth2UserService;

@Component
public class CustomOauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpSession httpSession;
    private final ObjectMapper objectMapper;

    public CustomOauth2SuccessHandler(HttpSession httpSession,
            ObjectMapper objectMapper) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        try {
            final SessionMember sessionMember = (SessionMember)httpSession.getAttribute(
                    CustomOAuth2UserService.MEMBER);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.getWriter()
                    .write(objectMapper.writeValueAsString(MemberResponse.from(sessionMember)));

            clearAuthenticationAttributes();
        } catch (IllegalStateException e) {
            logger.debug(LogMessage.format("Session is Invalided"));
        }
    }

    private void clearAuthenticationAttributes() {
        httpSession.removeAttribute(CustomOAuth2UserService.PRINCIPAL_OAUTHID_BEFORE_SAVING);
        httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
