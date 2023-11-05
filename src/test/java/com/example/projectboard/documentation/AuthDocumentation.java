package com.example.projectboard.documentation;

import com.example.projectboard.acceptance.AuthSteps;
import com.example.projectboard.auth.application.AuthService;
import com.example.projectboard.auth.application.dto.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

@DisplayName("인증 관련 문서화")
public class AuthDocumentation extends Documentation {

    @MockBean
    private AuthService authService;
    private final String email = "kimjisu@email.com";
    private final String password = "1234";

    @DisplayName("로그인")
    @Test
    void login() {
        // given : 선행조건 기술
        AuthRequest authRequest = authRequest();
        BDDMockito.given(authService.login(any())).willReturn("token");

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("auth-login",
                        requestFields(
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.token").type(JsonFieldType.STRING).description("JWT 토큰"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        AuthSteps.로그인_요청_문서화(requestSpecification, authRequest);
    }

    private AuthRequest authRequest() {
        return new AuthRequest(email, password);
    }
}
