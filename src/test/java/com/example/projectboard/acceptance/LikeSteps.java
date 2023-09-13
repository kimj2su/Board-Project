package com.example.projectboard.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

public class LikeSteps {

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
}
