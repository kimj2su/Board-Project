package com.example.projectboard.acceptance;

import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
}
