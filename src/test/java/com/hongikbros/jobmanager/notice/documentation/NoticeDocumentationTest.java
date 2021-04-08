package com.hongikbros.jobmanager.notice.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import com.hongikbros.jobmanager.common.auth.TestLoginMemberAdapter;
import com.hongikbros.jobmanager.common.documentation.Documentation;
import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.member.domain.LoginMember;
import com.hongikbros.jobmanager.notice.application.NoticeService;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.domain.notice.ApplyUrl;
import com.hongikbros.jobmanager.notice.domain.notice.Company;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.ui.NoticeController;
import com.hongikbros.jobmanager.notice.ui.dto.NoticeCreateRequest;

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
    @WithMockUser
    void should_generateNoticePostDocument() {
        //given
        final LoginMember loginMember = MemberFixture.LOGIN_MEMBER_EUNSEOK;
        final TestLoginMemberAdapter testLoginMemberAdapter = new TestLoginMemberAdapter(
                loginMember);

        final List<String> skillTags = Arrays.asList("Spring Boot", "docker");
        final Notice notice = TestObjectUtils.createNotice(
                1L,
                loginMember.getId(),
                "백앤드 개발자 상시모집",
                Company.from("icon.url"),
                TestObjectUtils.createSkills(skillTags),
                Duration.of(LocalDate.of(1000, 3, 1)
                        , LocalDate.of(3000, 10, 2)),
                ApplyUrl.from("http://apply.url")
        );

        NoticeResponse noticeResponse = NoticeResponse.of(notice);
        BDDMockito.given(
                noticeService.createNotice(eq(loginMember), any(NoticeCreateRequest.class)))
                .willReturn(noticeResponse);

        Map<String, Object> noticeCreateRequest = new HashMap<>();
        noticeCreateRequest.put("applyUrl", "http://apply.url");
        noticeCreateRequest.put("skillTags", skillTags);
        noticeCreateRequest.put("startDate",
                LocalDate.of(1000, 3, 1).format(DateTimeFormatter.ISO_LOCAL_DATE));
        noticeCreateRequest.put("endDate",
                LocalDate.of(3000, 10, 2).format(DateTimeFormatter.ISO_LOCAL_DATE));

        //when
        //@formatter:off
        given().
                auth().with(csrf().asHeader()).
                auth().principal(testLoginMemberAdapter).
                log().all().
                contentType("application/json").
                body(noticeCreateRequest).
                when().
                    post(NoticeController.API_NOTICE).
                then().log().all().
                    apply(document("api/notice",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Content-Type").attributes(getRequired(true)).description("application/json"),
                                headerWithName("X-CSRF-TOKEN").attributes(getRequired(true)).description("csrf token")
                        ),
                        requestFields(
                                fieldWithPath("applyUrl").type(JsonFieldType.STRING).attributes(getRequired(true)).description("공고의 url"),
                                fieldWithPath("skillTags").type(JsonFieldType.ARRAY).attributes(getRequired(false)).description("공고 skill Tags - SkillTag가 없을 경우 빈 배열을 포함해서 요청해야 합나다."),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(getRequired(true)).attributes(getDateFormat()).description("공고의 startDate"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(getRequired(true)).attributes(getDateFormat()).description("공고의 endDate")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("공고의 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("공고의 title"),
                                fieldWithPath("icon").type(JsonFieldType.STRING)
                                        .description("공고 company icon"),
                                fieldWithPath("skillTags").type(JsonFieldType.ARRAY)
                                        .description("공고 skill Tags").optional(),
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