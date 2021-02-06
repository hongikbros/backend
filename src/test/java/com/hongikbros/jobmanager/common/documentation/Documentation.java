package com.hongikbros.jobmanager.common.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
public class Documentation {
    @BeforeEach
    public void setUp(
            WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        RestAssuredMockMvc.webAppContextSetup(context,
                documentationConfiguration(restDocumentation),
                springSecurity()
        );
    }

    protected static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(modifyUris().removePort(), prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
