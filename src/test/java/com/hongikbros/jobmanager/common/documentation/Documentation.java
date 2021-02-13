package com.hongikbros.jobmanager.common.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.snippet.Attributes.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.web.context.WebApplicationContext;

import com.hongikbros.jobmanager.common.domain.security.CustomOauth2SuccessHandler;
import com.hongikbros.jobmanager.member.application.oauth.CustomOAuth2UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith({RestDocumentationExtension.class})
public class Documentation {

    @MockBean
    protected CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    protected CustomOauth2SuccessHandler customOauth2SuccessHandler;

    @BeforeEach
    public void setUp(
            WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        RestAssuredMockMvc.webAppContextSetup(context,
                documentationConfiguration(restDocumentation),
                springSecurity()
        );
    }

    protected static Attributes.Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }

    protected static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(modifyUris().removePort(), prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
