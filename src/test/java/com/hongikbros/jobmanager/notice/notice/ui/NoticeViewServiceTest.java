package com.hongikbros.jobmanager.notice.notice.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.fixture.sessionmember.SessionMemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.notice.NoticeDescription;
import com.hongikbros.jobmanager.notice.ui.NoticeResponse;
import com.hongikbros.jobmanager.notice.ui.NoticeViewService;
import com.hongikbros.jobmanager.skill.domain.skill.Skill;
import com.hongikbros.jobmanager.skill.domain.skill.SkillRepository;
import com.hongikbros.jobmanager.skill.domain.skillnotice.SkillNotice;
import com.hongikbros.jobmanager.skill.domain.skillnotice.SkillNoticeRepository;

@ExtendWith(MockitoExtension.class)
class NoticeViewServiceTest {
    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private SkillNoticeRepository skillNoticeRepository;

    @Mock
    private SkillRepository skillRepository;

    private NoticeViewService noticeViewService;

    @BeforeEach
    void setUp() {
        noticeViewService = new NoticeViewService(noticeRepository, skillNoticeRepository,
                skillRepository);
    }

    @DisplayName("공고 상세 내용을 조회하면 NoticeResponse dto를 반환한다.")
    @Test
    void should_returnNoticeResponse_whenShowNoticeIsRequested() {
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
        final SkillNotice skillNotice = TestObjectUtils.createSkillNotice(1L,
                new Association<>(1L), new Association<>(1L));
        final Skill skill = TestObjectUtils.createSkill(1L, "Spring Framework");

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));
        given(skillNoticeRepository.findAllByNoticeId(any())).willReturn(
                Collections.singletonList(skillNotice));
        given(skillRepository.findByIdIn(anyList())).willReturn(Collections.singletonList(skill));
        // when
        final NoticeResponse noticeResponse = noticeViewService.showNotice(1L,
                SessionMemberFixture.EUN_SEOK);
        // then
        assertAll(
                () -> assertThat(noticeResponse.getId()).isEqualTo(1L),
                () -> assertThat(noticeResponse.getTitle()).isEqualTo("백앤드 개발자 상시모집"),
                () -> assertThat(noticeResponse.getStartDate()).isEqualTo(LocalDateTime.MIN),
                () -> assertThat(noticeResponse.getEndDate()).isEqualTo(LocalDateTime.MAX),
                () -> assertThat(noticeResponse.getApplyUrl()).isEqualTo("hi.com"),
                () -> assertThat(noticeResponse.getDescription()).isEqualTo("잘하는 사람 뽑습니다.")
        );
    }

}