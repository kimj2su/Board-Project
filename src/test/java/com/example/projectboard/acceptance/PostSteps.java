package com.example.projectboard.acceptance;

import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.request.ModifyPostRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class PostSteps {

    public static CreatePostRequestDto request(String title, String content) {
        return new CreatePostRequestDto(title, content);
    }

    public static ExtractableResponse<Response> 게시글_생성_요청(String token, CreatePostRequestDto request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(request)
                .when().post("/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ModifyPostRequestDto modifyRequest(String title, String content) {
        return new ModifyPostRequestDto(title, content);
    }

    public static ExtractableResponse<Response> 게시글_수정_요청(Long id, String token, ModifyPostRequestDto request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .body(request)
                .when().patch("/posts/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 게시글_조회_요청(Long id, String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().get("/posts/" + id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 게시글_삭제_요청(Long id, String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().delete("/posts/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 게시글_생성_요청_문서화(String token, CreatePostRequestDto request, RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when().post("/posts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 게시글_조회_요청_문서화(String token, RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when().get("/posts/{id}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 게시글_수정_요청_문서화(String token, ModifyPostRequestDto request, RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when().patch("/posts/{id}", 1L)
                .then().log().all()
                .extract();
    }
}
