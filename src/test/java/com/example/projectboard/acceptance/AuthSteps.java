package com.example.projectboard.acceptance;

import com.example.projectboard.auth.application.dto.request.AuthRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
}
