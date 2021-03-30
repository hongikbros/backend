package com.hongikbros.jobmanager.notice.notice.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import com.hongikbros.jobmanager.common.auth.TestLoginMemberAdapter;
import com.hongikbros.jobmanager.common.documentation.Documentation;
import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.application.NoticeService;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.company.Company;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.ui.NoticeController;

@WebMvcTest(controllers = NoticeController.class)
class NoticeDocumentationTest extends Documentation {

    @MockBean
    private NoticeService noticeService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        super.setUp(webApplicationContext, restDocumentation);
    }

    @DisplayName("공고 상세 조회를 요청하면 ResponseEntity NoticeResponse 을 리턴한다.")
    @Test
    void should_generateNoticePostDocument() {
        //given
        final TestLoginMemberAdapter testLoginMemberAdapter = new TestLoginMemberAdapter(
                MemberFixture.LOGIN_MEMBER_EUNSEOK);

        final Company toss = TestObjectUtils.createCompany(1L, "icon.url");
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                testLoginMemberAdapter.getLoginMember().getId(),
                toss,
                "백앤드 개발자 상시모집",
                Duration.of(LocalDateTime.of(2020, 3, 10, 0, 0)
                        , LocalDateTime.of(2020, 5, 30, 0, 0)),
                ApplyUrl.from("hi.com")
        );
        NoticeResponse noticeResponse = NoticeResponse.of(notice);
        BDDMockito.given(noticeService.createNotice(anyLong(), any(), any()))
                .willReturn(noticeResponse);
        Map<String, String> noticeCreateRequest = new HashMap<>();
        noticeCreateRequest.put("applyUrl", "hi.com");
        noticeCreateRequest.put("startDate",
                LocalDateTime.of(2020, 3, 10, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        noticeCreateRequest.put("endDate",
                LocalDateTime.of(2020, 5, 30, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        //when
        //@formatter:off
        given().
                auth().principal(testLoginMemberAdapter).log().all().
                contentType("application/json").
                body(noticeCreateRequest).
                when().
                    post(NoticeController.API_NOTICE).
                then().log().all().
                    apply(document("api/notice",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseHeaders(
                                headerWithName("Set-Cookie").description("csrf token, JSEESIONID - Required")
                        ),
                        requestFields(
                                fieldWithPath("applyUrl").type(JsonFieldType.STRING).description("공고의 url"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat())
                                        .description("공고의 startDate"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat())
                                        .description("공고의 endDate")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("공고의 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("공고의 title"),
                                fieldWithPath("icon").type(JsonFieldType.STRING)
                                        .description("공고 company icon"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat())
                                        .description("공고의 startDate"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat())
                                        .description("공고의 endDate"),
                                fieldWithPath("applyUrl").type(JsonFieldType.STRING)
                                        .description("공고의 applyUrl")
                        )
                    )).
                extract();
        //@formatter:on
    }
}