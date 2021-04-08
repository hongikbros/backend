package com.hongikbros.jobmanager.notice.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.ui.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;
    @Mock
    private Scraper scraper;

    @InjectMocks
    private NoticeService noticeService;

    @DisplayName("url이 주어지면 새로운 공고를 만들어내는 uescase")
    @Test
    void should_createNotice_GivenUrlAndDuration() {
        // given
        final CurrentMember currentMember = MemberFixture.LOGIN_MEMBER_EUNSEOK;

        final String applyUrl = "apply.url";
        final List<String> skillTags = Arrays.asList("Spring Boot", "docker");
        final Duration duration = Duration.of(LocalDate.MIN, LocalDate.MAX);
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                currentMember.getId(),
                "백앤드 개발자 상시모집",
                Company.from("icon.url"),
                TestObjectUtils.createSkills(skillTags),
                duration,
                ApplyUrl.from(applyUrl)
        );

        NoticeCreateRequest noticeCreateRequest = new NoticeCreateRequest(
                applyUrl,
                skillTags,
                duration.getStartDate(),
                duration.getEndDate()
        );

        given(scraper.createNotice(currentMember.getId(), applyUrl, skillTags,
                duration)).willReturn(notice);

        // when
        noticeService.createNotice(currentMember, noticeCreateRequest);
        // then
        assertAll(
                () -> then(noticeRepository).should(times(1)).save(any()),
                () -> assertThat(notice.getId()).isEqualTo(1L),
                () -> assertThat(notice.getSkills().size()).isEqualTo(2),
                () -> assertThat(notice.getTitle()).isEqualTo("백앤드 개발자 상시모집"),
                () -> assertThat(notice.getDuration()).isEqualTo(duration),
                () -> assertThat(notice.getApplyUrl()).isEqualTo(ApplyUrl.from(applyUrl))
        );

    }
}
