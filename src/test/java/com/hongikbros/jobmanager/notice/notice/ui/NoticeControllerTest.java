package com.hongikbros.jobmanager.notice.notice.ui;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hongikbros.jobmanager.common.fixture.sessionmember.SessionMemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.notice.NoticeDescription;
import com.hongikbros.jobmanager.notice.ui.NoticeController;
import com.hongikbros.jobmanager.notice.ui.NoticeResponse;
import com.hongikbros.jobmanager.notice.ui.NoticeViewService;

@ExtendWith(MockitoExtension.class)
class NoticeControllerTest {

    @Mock
    private NoticeViewService noticeViewService;

    @InjectMocks
    private NoticeController noticeController;

    @DisplayName("공고 상세조회 Dto 타입의 ResponseEntity를 리턴함")
    @Test
    void should_returnResponseEntity() {
        // given
        final Company toss = TestObjectUtils.createCompany(1L, "toss", "icon.url");
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                toss,
                "백앤드 개발자 상시모집",
                Duration.of(LocalDateTime.MIN, LocalDateTime.MAX),
                ApplyUrl.from("hi.com"),
                NoticeDescription.from("잘하는 사람 뽑습니다.")
        );

        NoticeResponse noticeResponse = NoticeResponse.of(notice, toss);
        given(noticeViewService.showNotice(anyLong(), any())).willReturn(noticeResponse);

        // when
        final ResponseEntity<NoticeResponse> responseEntity = noticeController.showNotice(1L,
                SessionMemberFixture.EUN_SEOK);
        // then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).isNotNull()
        );

    }
}