package com.example.projectboard.acceptance;

import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class LikeSteps {

    public static ExtractableResponse<Response> 좋아요_상태_확인(String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().get("/likes/{postsId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 좋아요_증가(String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().post("/likes/{postsId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 좋아요_감소(String token) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(token)
                .when().delete("/likes/{postsId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();
    }

    public static ExtractableResponse<Response> 좋아요_증가_요청_문서화(String token, RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when().post("/likes/{postId}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_감소_요청_문서화(String token, RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when().delete("/likes/{postId}", 1L)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요_상태_확인_문서화(String token, RequestSpecification requestSpecification) {
        return requestSpecification
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when().get("/likes/{postId}", 1L)
                .then().log().all()
                .extract();
    }
}
