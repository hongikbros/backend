package com.hongikbros.jobmanager.notice.acceptence;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.hongikbros.jobmanager.acceptence.AcceptanceTest;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;

class NoticeAcceptanceTest extends AcceptanceTest {

    /**
     * Feature: 공고를 등록한다.<br/>
     *<br/>
     * Scenario: 회원은 자신이 북마크할 공고를 등록한다. <br/>
     *<br/>
     * Given 회원은 로그인한 상태이다. <br/>
     *
     * When 게시글을 삭제한다. <br/>
     * Then 게시글이 삭제된다. <br/>
     */
    @TestFactory
    Stream<DynamicTest> should_createNotice() {
        return Stream.of(dynamicTest("Feature: 공고를 등록한다.", () -> {
                    final String noticeUrl = DOMAIN + MOCK_SEVER_PORT + PATH;

                    final NoticeResponse notice = createNotice(noticeUrl, LocalDate.of(1000, 3, 1),
                            LocalDate.of(3000, 10, 2));
                    assertAll(
                            () -> assertThat(notice.getTitle()).isEqualTo(
                                    "[vertical팀] 앱/웹 UX/UI 기획 지원 체험형 인턴 모집"
                            ),
                            () -> assertThat(notice.getIcon()).isNotNull(),
                            () -> assertThat(notice.getStartDate()).isEqualTo(
                                    LocalDate.of(1000, 3, 1).format(
                                            DateTimeFormatter.ISO_LOCAL_DATE)),
                            () -> assertThat(notice.getEndDate()).isEqualTo(
                                    LocalDate.of(3000, 10, 2).format(DateTimeFormatter.ISO_LOCAL_DATE)),
                            () -> assertThat(notice.getApplyUrl()).isEqualTo(noticeUrl)
                    );
                }
        ));
    }

}
