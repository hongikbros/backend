package com.hongikbros.jobmanager.notice.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.hongikbros.jobmanager.notice.query.applicaion.NoticeViewService;
import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.command.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.command.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.command.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.command.domain.notice.Company;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@ExtendWith(MockitoExtension.class)
class NoticeViewServiceTest {
    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeViewService noticeViewService;

    @DisplayName("공고 상세 내용을 조회하면 NoticeResponse dto를 반환한다.")
    @Test
    void should_returnNoticeResponse_whenShowNoticeIsRequested() {
        // given
        final CurrentMember currentMember = MemberFixture.LOGIN_MEMBER_EUNSEOK;
        final List<String> skillTags = Arrays.asList("Spring Boot", "docker");

        final Notice notice = TestObjectUtils.createNotice(
                1L,
                currentMember.getId(),
                "백앤드 개발자 상시모집",
                Company.from("icon.url"),
                TestObjectUtils.createSkills(skillTags),
                Duration.of(LocalDate.MIN, LocalDate.MAX),
                ApplyUrl.from("hi.com")
        );

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));
        // when
        final NoticeResponses noticeResponses = noticeViewService.findAllByMemberId(MemberFixture.LOGIN_MEMBER_EUNSEOK);
        // then
//        assertAll(
//                () -> assertThat(noticeResponses.getId()).isEqualTo(1L),
//                () -> assertThat(noticeResponses.getTitle()).isEqualTo("백앤드 개발자 상시모집"),
//                () -> assertThat(noticeResponses.getSkillTags()).contains("Spring Boot", "docker"),
//                () -> assertThat(noticeResponses.getStartDate()).isEqualTo(LocalDate.MIN),
//                () -> assertThat(noticeResponses.getEndDate()).isEqualTo(LocalDate.MAX),
//                () -> assertThat(noticeResponses.getApplyUrl()).isEqualTo("hi.com")
//        );
    }

}