package com.hongikbros.jobmanager.notice.notice.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import com.hongikbros.jobmanager.common.auth.TestAuthenticationToken;
import com.hongikbros.jobmanager.common.documentation.Documentation;
import com.hongikbros.jobmanager.common.domain.Association;
import com.hongikbros.jobmanager.common.fixture.sessionmember.SessionMemberFixture;
import com.hongikbros.jobmanager.common.utils.TestObjectUtils;
import com.hongikbros.jobmanager.notice.company.Company;
import com.hongikbros.jobmanager.notice.notice.domain.ApplyUrl;
import com.hongikbros.jobmanager.notice.notice.domain.Duration;
import com.hongikbros.jobmanager.notice.notice.domain.Notice;
import com.hongikbros.jobmanager.notice.notice.domain.NoticeDescription;
import com.hongikbros.jobmanager.notice.notice.ui.NoticeController;
import com.hongikbros.jobmanager.notice.notice.ui.NoticeResponse;
import com.hongikbros.jobmanager.notice.notice.ui.NoticeViewService;
import com.hongikbros.jobmanager.skill.domain.Skill;

@WebMvcTest(controllers = NoticeController.class)
class NoticeDocumentationTest extends Documentation {

    @MockBean
    private NoticeViewService noticeViewService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        super.setUp(webApplicationContext, restDocumentation);
    }

    @DisplayName("공고 상세 조회를 요청하면 ResponseEntity NoticeResponse 을 리턴한다.")
    @Test
    void shouldGenerate_NoticeResponseDocument_whenNotLogin() {
        //given
        final TestAuthenticationToken testAuthenticationToken = new TestAuthenticationToken(
                SessionMemberFixture.EUN_SEOK);

        final Notice notice = TestObjectUtils.createNotice(
                1L,
                "백앤드 개발자 상시모집",
                Duration.of(LocalDateTime.MIN, LocalDateTime.MAX),
                ApplyUrl.from("hi.com"),
                new Association<Company>(1L),
                NoticeDescription.from("잘하는 사람 뽑습니다.")
        );
        final Company toss = TestObjectUtils.createCompany(1L, "toss", "icon.url");
        final Skill skill = TestObjectUtils.createSkill(1L, "Spring Framework");

        NoticeResponse noticeResponse = NoticeResponse.of(notice, toss,
                Collections.singletonList(skill), false);
        BDDMockito.given(noticeViewService.showNotice(anyLong(), any())).willReturn(noticeResponse);

        //when
        //@formatter:off
        given().
                auth().principal(testAuthenticationToken).log().all().
                when().
                    get(NoticeController.API_NOTICE + "/{id}", 1L).
                then().log().all().
                    apply(document("api/notice",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseHeaders(
                                headerWithName("Set-Cookie").description("csrf token - Required, JSEESIONID - Optional")
                        ),
                        pathParameters(
                                parameterWithName("id").description("공고의 id")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("공고의 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("공고의 title"),
                                fieldWithPath("company").type(JsonFieldType.STRING)
                                        .description("공고 company"),
                                fieldWithPath("icon").type(JsonFieldType.STRING)
                                        .description("공고 company icon"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat())
                                        .description("공고의 startDate"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat())
                                        .description("공고의 endDate"),
                                fieldWithPath("skills").type(JsonFieldType.ARRAY)
                                        .description("공고의 스킬 요구사항들"),
                                fieldWithPath("applyUrl").type(JsonFieldType.STRING)
                                        .description("공고의 applyUrl"),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("공고의 상세내용"),
                                fieldWithPath("bookmarkState").type(JsonFieldType.BOOLEAN)
                                        .description(
                                                "공고의 bookmark state\n - 비회원의 bookmarkState 는 항상 false"))
                ))
                .extract();
        //@formatter:on
    }
}