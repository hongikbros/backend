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
import com.hongikbros.jobmanager.notice.bookmark.BookmarkRepository;
import com.hongikbros.jobmanager.notice.company.Company;
import com.hongikbros.jobmanager.notice.company.CompanyRepository;
import com.hongikbros.jobmanager.notice.notice.domain.ApplyUrl;
import com.hongikbros.jobmanager.notice.notice.domain.Duration;
import com.hongikbros.jobmanager.notice.notice.domain.Notice;
import com.hongikbros.jobmanager.notice.notice.domain.NoticeDescription;
import com.hongikbros.jobmanager.notice.notice.domain.NoticeRepository;
import com.hongikbros.jobmanager.skill.domain.Skill;
import com.hongikbros.jobmanager.skill.domain.SkillNotice;
import com.hongikbros.jobmanager.skill.domain.SkillNoticeRepository;
import com.hongikbros.jobmanager.skill.domain.SkillRepository;

@ExtendWith(MockitoExtension.class)
class NoticeViewServiceTest {
    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private SkillNoticeRepository skillNoticeRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private BookmarkRepository bookmarkRepository;

    private NoticeViewService noticeViewService;

    @BeforeEach
    void setUp() {
        noticeViewService = new NoticeViewService(noticeRepository, companyRepository,
                skillNoticeRepository, skillRepository, bookmarkRepository);
    }

    @DisplayName("공고 상세 내용을 조회하면 NoticeResponse dto를 반환한다.")
    @Test
    void should_returnNoticeResponse_whenShowNoticeIsRequested() {
        // given
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                "백앤드 개발자 상시모집",
                Duration.of(LocalDateTime.MIN, LocalDateTime.MAX),
                ApplyUrl.from("hi.com"),
                new Association<>(1L),
                NoticeDescription.from("잘하는 사람 뽑습니다.")
        );
        final Company toss = TestObjectUtils.createCompany(1L, "toss", "icon.url");
        final SkillNotice skillNotice = TestObjectUtils.createSkillNotice(1L,
                new Association<>(1L), new Association<>(1L));
        final Skill skill = TestObjectUtils.createSkill(1L, "Spring Framework");

        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));
        given(companyRepository.findById(anyLong())).willReturn(Optional.of(toss));
        given(skillNoticeRepository.findAllByNoticeId(any())).willReturn(
                Collections.singletonList(skillNotice));
        given(skillRepository.findByIdIn(anyList())).willReturn(Collections.singletonList(skill));
        given(bookmarkRepository.existsBookmarkByMemberId(anyLong())).willReturn(true);
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
                () -> assertThat(noticeResponse.getDescription()).isEqualTo("잘하는 사람 뽑습니다."),
                () -> assertThat(noticeResponse.isBookmarkState()).isTrue()
        );
    }

}