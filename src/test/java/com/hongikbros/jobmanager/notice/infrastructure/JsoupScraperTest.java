package com.hongikbros.jobmanager.notice.infrastructure;

import static com.hongikbros.jobmanager.notice.infrastructure.JsoupScraperTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestFileIoUtils;
import com.hongikbros.jobmanager.notice.domain.notice.Duration;
import com.hongikbros.jobmanager.notice.domain.notice.Notice;
import com.hongikbros.jobmanager.notice.domain.scraper.Scraper;
import com.hongikbros.jobmanager.notice.infrastructure.exception.NotScrapingException;
import com.hongikbros.jobmanager.notice.infrastructure.scraper.JsoupScraper;
import com.hongikbros.jobmanager.notice.infrastructure.scraper.ScrapingExceptionCode;
import com.hongikbros.jobmanager.security.core.CurrentMember;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockServerSettings(ports = {MOCK_SEVER_PORT})
class JsoupScraperTest {
    public static final int MOCK_SEVER_PORT = 9000;
    private static final String PATH = "/job";
    private static final String DOMAIN = "http://localhost:";

    private ClientAndServer mockServer;
    private final Scraper scraper = new JsoupScraper();
    private final Duration duration = Duration.of(LocalDate.MIN, LocalDate.MAX);
    private final CurrentMember currentMember = MemberFixture.LOGIN_MEMBER_EUNSEOK;

    @BeforeAll
    void beforeAll() {
        mockServer = ClientAndServer.startClientAndServer();
    }

    @AfterEach
    void tearDown() {
        new MockServerClient("localhost", MOCK_SEVER_PORT).reset();
    }

    @DisplayName("url이 주어지면 해당 공고를 만들어 post하는 기능")
    @Test
    void should_createNotice_GivenUrl() {
        // given
        final String noticeUrl = DOMAIN + MOCK_SEVER_PORT + PATH;
        final byte[] response = TestFileIoUtils.loadFileFromClasspath(
                "/static/kakao_recruiting_test_page.html");

        new MockServerClient("localhost", MOCK_SEVER_PORT)
                .when(request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(PATH)
                )
                .respond(response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withBody(response)
                );
        // when
        Notice notice = scraper.createNotice(currentMember.getId(), noticeUrl, duration);
        // then
        assertAll(
                () -> assertThat(notice.getCompany().getIcon()).isNotNull(),
                () -> assertThat(notice.getTitle()).isEqualTo(
                        "[vertical팀] 앱/웹 UX/UI 기획 지원 체험형 인턴 모집"
                ),
                () -> assertThat(notice.getDuration().getStartDate()).isEqualTo(LocalDate.MIN),
                () -> assertThat(notice.getDuration().getEndDate()).isEqualTo(LocalDate.MAX),
                () -> assertThat(notice.getApplyUrl().getRedirectUrl()).isEqualTo(noticeUrl)
        );
    }

    @DisplayName("연결할 수 없는 url 요청으로 크롤링 시 예외 테스트")
    @Test
    void should_Exception_GivenIncorrectUrlIsGiven() {
        //given
        new MockServerClient("localhost", MOCK_SEVER_PORT)
                .when(request()
                        .withMethod(HttpMethod.GET.name()))
                .respond(response()
                        .withStatusCode(HttpStatus.BAD_REQUEST.value()));
        final Long id = currentMember.getId();

        //then
        assertThatThrownBy(
                () -> scraper.createNotice(id, DOMAIN + MOCK_SEVER_PORT,
                        duration))
                .isInstanceOf(NotScrapingException.class)
                .hasMessage(ScrapingExceptionCode.URL_NOT_CONNECT.getMessage());
    }

    @DisplayName("찾을 수 없는 URL 예외 테스트")
    @Test
    void should_Exception_GivenNotFoundUrl() {
        //given
        new MockServerClient("localhost", MOCK_SEVER_PORT)
                .when(request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(PATH))
                .respond(response()
                        .withStatusCode(HttpStatus.NOT_FOUND.value())
                );
        final Long id = currentMember.getId();

        //then
        assertThatThrownBy(
                () -> scraper.createNotice(id, DOMAIN + MOCK_SEVER_PORT + PATH,
                        duration))
                .isInstanceOf(NotScrapingException.class)
                .hasMessage(ScrapingExceptionCode.NOT_FOUND_URL.getMessage());
    }

    @DisplayName("너무 많은 요청을 보내 발생하는 429 예외 테스트")
    @Test
    void should_Exception_GivenTooManyRequest() {
        //given
        new MockServerClient("localhost", MOCK_SEVER_PORT)
                .when(request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(PATH))
                .respond(response()
                        .withStatusCode(HttpStatus.TOO_MANY_REQUESTS.value())
                );
        final Long id = currentMember.getId();

        //then
        assertThatThrownBy(
                () -> scraper.createNotice(id, DOMAIN + MOCK_SEVER_PORT + PATH,
                        duration))
                .isInstanceOf(NotScrapingException.class)
                .hasMessage(ScrapingExceptionCode.TOO_MANY_REQUEST.getMessage());
    }

    @AfterAll
    void afterAll() {
        mockServer.stop();
    }
}