package com.hongikbros.jobmanager.notice.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.application.NoticeService;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.ui.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@ExtendWith(MockitoExtension.class)
class NoticeControllerTest {

    @Mock
    private NoticeService noticeService;

    @InjectMocks
    private NoticeController noticeController;

    @DisplayName("공고 상세조회 Dto 타입의 ResponseEntity를 리턴함")
    @Test
    void should_returnResponseEntity() {
        // given
        final CurrentMember currentMember = MemberFixture.LOGIN_MEMBER_EUNSEOK;
        final Company toss = TestObjectUtils.createCompany(1L, "icon.url");
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                currentMember.getId(),
                toss,
                "백앤드 개발자 상시모집",
                Duration.of(LocalDate.MIN, LocalDate.MAX),
                ApplyUrl.from("hi.com")
        );

        NoticeCreateRequest noticeCreateRequest = new NoticeCreateRequest(
                "apply.url",
                LocalDate.MIN,
                LocalDate.MAX
        );
        NoticeResponse noticeResponse = NoticeResponse.of(notice);
        given(noticeService.createNotice(anyLong(), any(), any())).willReturn(noticeResponse);

        //when
        final ResponseEntity<NoticeResponse> responseEntity = noticeController.createNotice(
                noticeCreateRequest, MemberFixture.LOGIN_MEMBER_EUNSEOK);
        // then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(responseEntity.getBody()).isNotNull()
        );
    }
}