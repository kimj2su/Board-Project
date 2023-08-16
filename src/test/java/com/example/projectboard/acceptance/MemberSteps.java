package com.example.projectboard.acceptance;

import com.example.projectboard.member.application.dto.v1.request.CreateMemberRequestDto;
import com.example.projectboard.member.application.dto.v1.request.ModifyMemberRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberSteps {

    public static CreateMemberRequestDto request(String name, String email, String password) {
        return new CreateMemberRequestDto(name, email, password);
    }

    public static ModifyMemberRequestDto request(String name, String email) {
        return new ModifyMemberRequestDto(name, email);
    }

    public static ExtractableResponse<Response> 회원_생성_요청(CreateMemberRequestDto request) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 회원_조회_요청(Long id) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{id}", id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 회원_수정_요청(ModifyMemberRequestDto request) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/members/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 회원_삭제_요청(Long id) {

        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/members/{id}", id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }
}
