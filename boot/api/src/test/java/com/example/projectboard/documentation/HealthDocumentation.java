package com.example.projectboard.documentation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

@DisplayName("Health")
public class HealthDocumentation extends Documentation {

    @DisplayName("헬스 체크 문서화")
    @Test
    void healthCheck() {
        // given : 선행조건 기술

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("health-check",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).optional().description("응답 데이터"),
                                fieldWithPath("data.serverPort").type(JsonFieldType.STRING).optional().description("서버 포트"),
                                fieldWithPath("data.profile").type(JsonFieldType.STRING).optional().description("서버 프로파일"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        헬스_체크_문서화(requestSpecification);
    }

    private ExtractableResponse<Response> 헬스_체크_문서화(RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/health")
                .then().log().all()
                .extract();
    }
}
