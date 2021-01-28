package com.hongikbros.jobmanager.common.domain.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongikbros.jobmanager.member.application.member.SessionMember;
import com.hongikbros.jobmanager.member.application.oauth.CustomOAuth2UserService;
import com.hongikbros.jobmanager.member.fixture.member.MemberFixture;

@ExtendWith(MockitoExtension.class)
class CustomOauth2SuccessHandlerTest {

    @Mock
    private HttpServletRequest request;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletResponse response;
    @Mock
    private Authentication authentication;

    @Mock
    private HttpSession httpSession;
    @Mock
    private ObjectMapper objectMapper;

    private CustomOauth2SuccessHandler customOauth2SuccessHandler;

    @BeforeEach
    void setUp() {
        customOauth2SuccessHandler = new CustomOauth2SuccessHandler(httpSession, objectMapper);
    }

    @DisplayName("Authentication 과정이 끝나고 SuccessHandler에 의해 response에 write 된다")
    @Test
    void should_writeResponse_whenAuthenticationIsSuccessful() throws IOException {
        // given
        given(httpSession.getAttribute(any())).willReturn(SessionMember.of(MemberFixture.MEMBER1));
        // when
        customOauth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);
        // then
        assertAll(
                () -> then(httpSession).should(times(1))
                        .getAttribute(CustomOAuth2UserService.MEMBER),
                () -> then(response).should(times(1))
                        .setContentType(MediaType.APPLICATION_JSON_VALUE),
                () -> then(response).should(times(1)).setStatus(HttpStatus.OK.value()),
                () -> then(response).should(times(1)).setCharacterEncoding("utf-8"),
                () -> then(response.getWriter()).should(times(1))
                        .write(objectMapper.writeValueAsString(any()))
        );
    }
}