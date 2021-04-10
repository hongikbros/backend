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
import com.hongikbros.jobmanager.security.core.CurrentMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NoticeControllerTest {

    @Mock
    private NoticeViewListService noticeViewListService;

    @InjectMocks
    private NoticeController noticeController;

    @DisplayName("공고 전체조회 NoticeResponses Dto 타입의 ResponseEntity를 리턴함")
    @Test
    void should_returnResponseEntity() {
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

        final NoticeDetail noticeDetail = NoticeDetail.of(notice);
        final NoticeResponses noticeResponses = NoticeResponses.of(Collections.singletonList(noticeDetail));

        given(noticeViewListService.findAllByMemberId(any())).willReturn(noticeResponses);

        //when
        final ResponseEntity<NoticeResponses> responseEntity = noticeController.findAll(currentMember);
        // then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).isNotNull()
        );
    }
}