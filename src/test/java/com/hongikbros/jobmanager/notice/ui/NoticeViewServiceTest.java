package com.hongikbros.jobmanager.notice.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.security.core.CurrentMember;

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

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));
        // when
        final NoticeResponse noticeResponse = noticeViewService.showNotice(1L,
                MemberFixture.LOGIN_MEMBER_EUNSEOK);
        // then
        assertAll(
                () -> assertThat(noticeResponse.getId()).isEqualTo(1L),
                () -> assertThat(noticeResponse.getTitle()).isEqualTo("백앤드 개발자 상시모집"),
                () -> assertThat(noticeResponse.getStartDate()).isEqualTo(LocalDate.MIN),
                () -> assertThat(noticeResponse.getEndDate()).isEqualTo(LocalDate.MAX),
                () -> assertThat(noticeResponse.getApplyUrl()).isEqualTo("hi.com")
        );
    }

}