package com.example.projectboard.acceptance;

import com.example.projectboard.auth.application.dto.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AuthSteps {

    public static AuthRequest request(String email, String password) {
        return new AuthRequest(email, password);
    }

    public static ExtractableResponse<Response> 로그인_요청(AuthRequest request) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/auth/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 내정보_조회_요청(String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().get("/auth/myPage")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 로그인_요청_문서화(RequestSpecification requestSpecification, AuthRequest request) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/auth/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내정보_조회_요청_문서화(RequestSpecification requestSpecification, String token) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/myPage")
                .then().log().all()
                .extract();
    }
}
