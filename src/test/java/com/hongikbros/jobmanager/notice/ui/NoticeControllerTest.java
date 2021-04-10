package com.hongikbros.jobmanager.notice.ui;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.hongikbros.jobmanager.notice.query.applicaion.NoticeViewService;
import com.hongikbros.jobmanager.notice.query.dao.NoticeViewDao;
import com.hongikbros.jobmanager.notice.query.dto.NoticeResponses;
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
import com.hongikbros.jobmanager.notice.command.application.NoticeService;
import com.hongikbros.jobmanager.notice.command.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.command.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.command.domain.notice.Company;
import com.hongikbros.jobmanager.notice.command.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.command.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.command.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.security.core.CurrentMember;

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

        NoticeResponses noticeResponses = NoticeResponses.of(Collections.singletonList(notice));
        given(noticeViewService.findAllByMemberId(any())).willReturn(noticeResponses);

        //when
        final ResponseEntity<NoticeResponses> responseEntity = noticeController.findAll(currentMember);
        // then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody()).isNotNull()
        );
    }
}