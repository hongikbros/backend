package com.hongikbros.jobmanager.notice.infrastructure;

import static com.hongikbros.jobmanager.notice.infrastructure.JsoupScraperTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.http.HttpStatus;

import com.hongikbros.jobmanager.common.utils.TestFileIoUtils;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.infrastructure.scraper.JsoupScraper;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {MOCK_SEVER_PORT})
class JsoupScraperTest {
    public static final int MOCK_SEVER_PORT = 9000;
    private Scraper scraper;
    private ClientAndServer mockServer;
    private byte[] response;

    @BeforeEach
    void setUp(ClientAndServer clientAndServer) {
        scraper = new JsoupScraper();
        response = TestFileIoUtils.loadFileFromClasspath("/static/kakao_recruiting_test_page.html");
        this.mockServer = clientAndServer;
    }

    @AfterEach
    void tearDown() {
        this.mockServer.stop();
    }

    @DisplayName("url이 주어지면 해당 공고를 만들어 post하는 기능")
    @Test
    void should_whenUrlIsGiven_makeNotice() {
        // given
        String noticeUrl = "https://careers.kakaoenterprise.com/job";

        new MockServerClient("localhost", MOCK_SEVER_PORT)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath(noticeUrl)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.OK.value())
                                .withBody(response)
                );
        // when
        Notice notice = scraper.createNotice(noticeUrl);
        // then
        assertAll(
                () -> assertThat(notice.getCompany().getName()).isEqualTo("kakaoenterprise"),
                () -> assertThat(notice.getApplyUrl().getRedirectUrl()).isEqualTo(
                        "https://careers.kakaoenterprise.com/job")
        );
    }
}