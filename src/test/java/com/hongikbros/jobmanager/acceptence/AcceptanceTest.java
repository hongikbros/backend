package com.hongikbros.jobmanager.acceptence;

import static com.hongikbros.jobmanager.acceptence.AcceptanceTest.*;
import static org.mockserver.model.HttpRequest.*;
import static org.mockserver.model.HttpResponse.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongikbros.jobmanager.common.auth.TestLoginMemberAdapter;
import com.hongikbros.jobmanager.common.fixture.member.MemberFixture;
import com.hongikbros.jobmanager.common.utils.TestFileIoUtils;
import com.hongikbros.jobmanager.notice.application.dto.NoticeResponse;
import com.hongikbros.jobmanager.notice.ui.NoticeController;
import com.hongikbros.jobmanager.notice.ui.dto.NoticeCreateRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockServerSettings(ports = {MOCK_SEVER_PORT})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    protected static final int MOCK_SEVER_PORT = 9000;
    protected static final String PATH = "/job";
    protected static final String DOMAIN = "http://localhost:";
    private static final String DELIMITER = "/";

    @LocalServerPort
    protected int port;

    @Autowired
    private DataBaseClean dataBaseClean;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientAndServer mockServer;

    private final TestLoginMemberAdapter testLoginMemberAdapter = new TestLoginMemberAdapter(
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

    private static MockMvcRequestSpecification given() {
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

    protected NoticeResponse createNotice(String url, LocalDate startDate, LocalDate endDate) throws
            JsonProcessingException {
        mocking200ScrapingServer();

        NoticeCreateRequest noticeCreateRequest = new NoticeCreateRequest(url, startDate, endDate);
        final String createNoticeRequest = objectMapper.writeValueAsString(noticeCreateRequest);

        return post(createNoticeRequest, NoticeResponse.class);
    }

    protected <T> T post(String requestJson, Class<T> responseType) {
        // @formatter:off
        return
                given().auth().principal(testLoginMemberAdapter).
                        contentType(MediaType.APPLICATION_JSON_VALUE).
                        accept(MediaType.APPLICATION_JSON_VALUE).
                        body(requestJson).
                when().
                        post(NoticeController.API_NOTICE).
                then().
                        log().all().
                        statusCode(HttpStatus.CREATED.value()).
                        extract().as(responseType);
        // @formatter:on
    }

}
