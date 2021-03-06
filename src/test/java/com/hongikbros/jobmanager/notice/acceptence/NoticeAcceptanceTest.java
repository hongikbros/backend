package com.hongikbros.jobmanager.notice.acceptence;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hongikbros.jobmanager.acceptence.AcceptanceTest;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeChangeRequest;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.notice.ui.NoticeController;

class NoticeAcceptanceTest extends AcceptanceTest {

    /**
     * Feature: 공고를 관리한다. <br/>
     * <br/>
     * Scenario: 회원은 자신이 북마크할 공고를 등록한다. <br/>
     * <br/>
     * Given 회원은 로그인한 상태이다. <br/>
     * <p>
     * When 게시글 등록을 요청한다. <br/>
     * Then 게시글이 등록된다. <br/>
     * <p>
     * When 게시글을 조회한다. <br/>
     * Then 게시글이 조회된다.
     * <p>
     * When 게시글 삭제 요청한다. <br/>
     * Then 게시글이 삭제된다.
     */
    @DisplayName("Feature: 공고를 관리한다.")
    @TestFactory
    Stream<DynamicTest> should_createNotice() throws JsonProcessingException {
        final NoticeDetail setupNotice = createNotice(DOMAIN + MOCK_SEVER_PORT + PATH,
                Arrays.asList("JPA", "JAVA"),
                LocalDate.of(2000, 3, 1),
                LocalDate.of(2000, 10, 2));

        return Stream.of(
                dynamicTest("공고를 만드는 요청으로 새로운 공고를 생성한다.", () -> {
                    final String noticeUrl = DOMAIN + MOCK_SEVER_PORT + PATH;
                    final List<String> skillTags = Arrays.asList("Spring Boot", "docker");

                    final NoticeDetail notice = createNotice(noticeUrl,
                            skillTags,
                            LocalDate.of(2000, 3, 1),
                            LocalDate.of(2000, 10, 2));
                    assertAll(
                            () -> assertThat(notice.getTitle()).isEqualTo(
                                    "[vertical팀] 앱/웹 UX/UI 기획 지원 체험형 인턴 모집"
                            ),
                            () -> assertThat(notice.getIcon()).isNotNull(),
                            () -> assertThat(notice.getStartDate()).isEqualTo(
                                    LocalDate.of(2000, 3, 1)
                                            .format(DateTimeFormatter.ISO_LOCAL_DATE)),
                            () -> assertThat(notice.getEndDate()).isEqualTo(
                                    LocalDate.of(2000, 10, 2)
                                            .format(DateTimeFormatter.ISO_LOCAL_DATE)),
                            () -> assertThat(notice.getApplyUrl()).isEqualTo(noticeUrl)
                    );
                }),
                dynamicTest("전체 공고를 불러온다.", () -> {
                    NoticeResponses noticeResponses = findAllNotices();

                    assertThat(noticeResponses.getNoticeDetails().size()).isEqualTo(2);
                }),
                dynamicTest("게시글 수정 요청한다.", () -> {

                    NoticeChangeRequest noticeChangeRequest = new NoticeChangeRequest(
                            "재무시스템",
                            "icon.url",
                            Arrays.asList("JPA", "MyBatis"),
                            LocalDate.now(),
                            LocalDate.of(2021, 4, 15),
                            "http://kakao.com"
                    );

                    final String request = objectMapper.writeValueAsString(noticeChangeRequest);

                    updateById(NoticeController.API_NOTICE, setupNotice.getId(), request);
                }),
                dynamicTest("게시글 삭제 요청한다.",
                        () -> deleteById(NoticeController.API_NOTICE, setupNotice.getId()))
        );
    }

    /**
     * Feature: 공고를 잘못된 공고 요청시 에러를 던진다.<br/>
     * <br/>
     * Scenario: 회원은 자신이 북마크할 공고를 등록한다. <br/>
     * <br/>
     * Given 회원은 로그인한 상태이다. <br/>
     * <p>
     * When 게시글을 잘못된 요청을 보낸다. <br/>
     * Then 게시글이 에러를 던진다. <br/>
     */
    @DisplayName("Feature: 공고를 잘못된 공고 요청시 에러를 던진다.")
    @Test
    void should_createException_whenBadRequest() throws JsonProcessingException {
        final String noticeUrl = DOMAIN + MOCK_SEVER_PORT + PATH;
        NoticeCreateRequest noticeCreateRequest = new NoticeCreateRequest(noticeUrl, null,
                LocalDate.of(2000, 3, 1),
                LocalDate.of(2000, 10, 2));
        final String exceptionNoticeRequest = objectMapper.writeValueAsString(noticeCreateRequest);

        // @formatter:off
        given().
                auth().with(csrf()).
                auth().principal(testLoginMemberAdapter).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
                body(exceptionNoticeRequest).
        when().
                post(NoticeController.API_NOTICE).
        then().
                log().all().
                statusCode(HttpStatus.BAD_REQUEST.value());
        // @formatter:on
    }

}
