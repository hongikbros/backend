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
import com.hongikbros.jobmanager.member.application.oauth.SessionMember;

@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpSession httpSession;
    private final ObjectMapper objectMapper;

    public Oauth2SuccessHandler(HttpSession httpSession,
            ObjectMapper objectMapper) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        try {
            final SessionMember sessionMember = (SessionMember)httpSession.getAttribute("user");

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(sessionMember));

            httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } catch (IllegalStateException e) {
            logger.debug(LogMessage.format("Session is Invalided"));
        }
    }
}
