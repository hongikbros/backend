package com.hongikbros.jobmanager.security.oauth2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongikbros.jobmanager.common.fixture.sessionmember.SessionMemberFixture;
import com.hongikbros.jobmanager.member.domain.LoginMemberAdapter;

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

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private SecurityContext securityContext;

    @Mock
    private LoginMemberAdapter loginMemberAdapter;

    private CustomOauth2SuccessHandler customOauth2SuccessHandler;

    @BeforeEach
    void setUp() {
        customOauth2SuccessHandler = new CustomOauth2SuccessHandler(httpSession, objectMapper);

        given(securityContext.getAuthentication().getPrincipal()).willReturn(loginMemberAdapter);
        given(loginMemberAdapter.getSessionMember()).willReturn(SessionMemberFixture.EUN_SEOK);

        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @DisplayName("Authentication 과정이 끝나고 SuccessHandler에 의해 response에 write 된다")
    @Test
    @WithMockUser
    void should_writeResponse_whenAuthenticationIsSuccessful() throws IOException {
        // when
        customOauth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);
        // then
        assertAll(
                () -> then(response).should(times(1))
                        .setContentType(MediaType.APPLICATION_JSON_VALUE),
                () -> then(response).should(times(1)).setStatus(HttpStatus.OK.value()),
                () -> then(response).should(times(1)).setCharacterEncoding("utf-8"),
                () -> then(response.getWriter()).should(times(1))
                        .write(objectMapper.writeValueAsString(any()))
        );
    }
}