package com.hongikbros.jobmanager.acceptence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongikbros.jobmanager.common.auth.TestLoginMemberAdapter;
import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestFileIoUtils;
import com.hongikbros.jobmanager.notice.command.application.dto.NoticeCreateRequest;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeDetail;
import com.hongikbros.jobmanager.notice.query.applicaion.dto.NoticeResponses;
import com.hongikbros.jobmanager.notice.ui.NoticeController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.hongikbros.jobmanager.acceptence.AcceptanceTest.MOCK_SEVER_PORT;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockServerSettings(ports = {MOCK_SEVER_PORT})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    protected static final int MOCK_SEVER_PORT = 9000;
    protected static final String PATH = "/job";
    protected static final String DOMAIN = "http://localhost:";
    private static final String DELIMITER = "/";

    @Autowired
    private DataBaseClean dataBaseClean;

    private ClientAndServer mockServer;

    @Autowired
    protected ObjectMapper objectMapper;

    protected final TestLoginMemberAdapter testLoginMemberAdapter = new TestLoginMemberAdapter(
            MemberFixture.LOGIN_MEMBER_EUNSEOK);

    @BeforeAll
    void beforeAll() {
        mockServer = ClientAndServer.startClientAndServer();
    }

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        RestAssuredMockMvc.webAppContextSetup(context, springSecurity());
    }

    @AfterEach
    void tearDown() {
        dataBaseClean.execute();
        new MockServerClient("localhost", MOCK_SEVER_PORT).reset();
    }

    @AfterAll
    void afterAll() {
        mockServer.stop();
    }

    protected static MockMvcRequestSpecification given() {
        return RestAssuredMockMvc.given().log().all();
    }

    protected Long extractId(String location) {
        List<String> tokens = Arrays.asList(location.split(DELIMITER));
        String id = tokens.get(tokens.size() - 1);
        return Long.parseLong(id);
    }

    protected void mocking200ScrapingServer() {
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
    }

    protected NoticeResponses findAllNotices() {
        return findAll(NoticeController.API_NOTICE, NoticeResponses.class);
    }

    protected NoticeDetail createNotice(String noticeUrl, List<String> skillTags,
                                        LocalDate startDate, LocalDate endDate) throws
            JsonProcessingException {
        mocking200ScrapingServer();

        NoticeCreateRequest noticeCreateRequest = new NoticeCreateRequest(noticeUrl, skillTags,
                startDate, endDate);
        final String createNoticeRequest = objectMapper.writeValueAsString(noticeCreateRequest);

        return post(NoticeController.API_NOTICE, createNoticeRequest, NoticeDetail.class);
    }

    protected <T> T post(String path, String requestJson, Class<T> responseType) {
        // @formatter:off
        return
                given().
                        auth().with(csrf()).
                        auth().principal(testLoginMemberAdapter).
                        contentType(MediaType.APPLICATION_JSON_VALUE).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        body(requestJson).
                when().
                        post(path).
                then().
                        log().all().
                        statusCode(HttpStatus.CREATED.value()).
                        extract().as(responseType);
        // @formatter:on
    }

    private <T> T findAll(String path, Class<T> responseType) {
        // @formatter:off
        return
                given().
                        auth().principal(testLoginMemberAdapter).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                when().
                        get(path).
                then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().as(responseType);
        // @formatter:on
    }
}
