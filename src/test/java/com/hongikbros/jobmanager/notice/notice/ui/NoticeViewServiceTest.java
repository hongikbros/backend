package com.hongikbros.jobmanager.notice.notice.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hongikbros.jobmanager.common.fixture.sessionmember.SessionMemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.ui.NoticeResponse;
import com.hongikbros.jobmanager.notice.ui.NoticeViewService;

@ExtendWith(MockitoExtension.class)
class NoticeViewServiceTest {
    @Mock
    private NoticeRepository noticeRepository;

    private NoticeViewService noticeViewService;

    @BeforeEach
    void setUp() {
        noticeViewService = new NoticeViewService(noticeRepository);
    }

    @DisplayName("공고 상세 내용을 조회하면 NoticeResponse dto를 반환한다.")
    @Test
    void should_returnNoticeResponse_whenShowNoticeIsRequested() {
        // given
        final Company toss = TestObjectUtils.createCompany(1L, "icon.url");
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                toss,
                "백앤드 개발자 상시모집",
                Duration.of(LocalDateTime.MIN, LocalDateTime.MAX),
                ApplyUrl.from("hi.com")
        );

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));
        // when
        final NoticeResponse noticeResponse = noticeViewService.showNotice(1L,
                SessionMemberFixture.EUN_SEOK);
        // then
        assertAll(
                () -> assertThat(noticeResponse.getId()).isEqualTo(1L),
                () -> assertThat(noticeResponse.getTitle()).isEqualTo("백앤드 개발자 상시모집"),
                () -> assertThat(noticeResponse.getStartDate()).isEqualTo(LocalDateTime.MIN),
                () -> assertThat(noticeResponse.getEndDate()).isEqualTo(LocalDateTime.MAX),
                () -> assertThat(noticeResponse.getApplyUrl()).isEqualTo("hi.com")
        );
    }

}