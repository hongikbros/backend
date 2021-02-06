package com.hongikbros.jobmanager.notice.ui;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import com.hongikbros.jobmanager.common.config.SecurityConfig;
import com.hongikbros.jobmanager.common.documentation.Documentation;
import com.hongikbros.jobmanager.notice.application.NoticeDto;
import com.hongikbros.jobmanager.notice.application.NoticeService;

@WebMvcTest(controllers = NoticeController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
class NoticeDocumentationTest extends Documentation {

    @MockBean
    private NoticeService noticeService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        super.setUp(webApplicationContext, restDocumentation);
    }

    @DisplayName("공고 상세 조회를 요청하면 ResponseEntity NoticeResponse을 리턴한다.")
    @Test
    @WithMockUser
    void should_returnNoticeResponseEntity_whenRequestGetNotice() {
        //given
        BDDMockito.given(noticeService.showNotice(anyLong())).willReturn(new NoticeDto(1L));

        //when
        given().log().all().
                accept(MediaType.APPLICATION_JSON_VALUE).
                when().
                get(NoticeController.API_NOTICE + "/{id}", 1L).
                then().log().all()
                .apply(document("api/notice",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("accept").description(
                                        "클라이언트가 받을 Content-type")),
                        pathParameters(
                                parameterWithName("id").description("공고의 id")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("공고의 id"))
                ))
                .extract();

    }
}