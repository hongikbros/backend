package com.hongikbros.jobmanager.notice.ui;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.command.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.command.domain.notice.Company;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.query.applicaion.NoticeViewListService;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.hongikbros.jobmanager.security.core.CurrentMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NoticeViewListServiceTest {
    @Mock
    private NoticeViewDao noticeViewDao;

    @InjectMocks
    private NoticeViewListService noticeViewListService;

    @DisplayName("공고 상세 내용을 조회하면 NoticeResponses dto를 반환한다.")
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
        given(noticeViewDao.findByMemberId(anyLong())).willReturn(Collections.singletonList(NoticeDetail.of(notice)));

        // when
        final NoticeResponses noticeResponses = noticeViewListService.findAllByMemberId(MemberFixture.LOGIN_MEMBER_EUNSEOK);
        // then
        assertThat(noticeResponses.getNoticeResponses().size()).isEqualTo(1);
    }

}