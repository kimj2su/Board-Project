package com.example.projectboard.acceptance;

import com.example.projectboard.auth.application.dto.request.AuthRequest;
import com.example.projectboard.member.application.dto.v1.request.CreateMemberRequestDto;
import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.projectboard.acceptance.AuthSteps.로그인_요청;
import static com.example.projectboard.acceptance.MemberSteps.request;
import static com.example.projectboard.acceptance.MemberSteps.회원_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 관련 기능")
public class PostAcceptanceTest extends AcceptanceTest {

    private final String name = "김지수";
    private final String email = "kjs3268@gmail.com";
    private final String password = "1234";
    private String token;

    @BeforeEach
    void login() {
        CreateMemberRequestDto memberRequest = request(name, email, password);
        회원_생성_요청(memberRequest);
        AuthRequest request = AuthSteps.request(email, password);
        ExtractableResponse<Response> response = 로그인_요청(request);
        token = response.jsonPath().getString("data.token");
    }

    /**
     * <pre>
     *     Feature: 게시글 작성
     *      Given : 회원을 생성한다.
     *      Given : 로그인을 한다.
     *      Given : 게시글 작성 요청을 보낸다.
     *      When : 게시글 작성 요청을 보내면
     *      Then : 게시글이 작성된다.
     * </pre>
     */
    @DisplayName("게시글 작성")
    @Test
    void createPost() {
        // given : 선행조건 기술
        CreatePostRequestDto request = PostSteps.request("제목", "내용");

        // when : 기능 수행
        ExtractableResponse<Response> response = PostSteps.게시글_생성_요청(token, request);

        // then : 결과 확인
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("result")).isEqualTo("SUCCESS");
        assertThat(response.jsonPath().getString("data.id")).isNotNull();
        assertThat(response.jsonPath().getString("data.title")).isEqualTo("제목");
        assertThat(response.jsonPath().getString("data.content")).isEqualTo("내용");
    }
}
