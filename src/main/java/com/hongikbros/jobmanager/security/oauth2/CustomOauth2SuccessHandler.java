package com.hongikbros.jobmanager.security.oauth2;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongikbros.jobmanager.member.application.MemberResponse;
import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.member.domain.LoginMemberAdapter;

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
            final LoginMember loginMember = ((LoginMemberAdapter)
                    (SecurityContextHolder.getContext().getAuthentication().getPrincipal())
            ).getSessionMember();

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.getWriter()
                    .write(objectMapper.writeValueAsString(MemberResponse.from(loginMember)));

            clearAuthenticationAttributesInSession(request);
        } catch (IllegalStateException e) {
            logger.debug(LogMessage.format("Session is Invalided"));
        }
    }

    private void clearAuthenticationAttributesInSession(HttpServletRequest request) {
        if (Objects.nonNull(httpSession)) {
            httpSession.removeAttribute(CustomOAuth2UserService.PRINCIPAL_OAUTHID_BEFORE_SAVING);
        }
        clearAuthenticationAttributes(request);
    }
}
