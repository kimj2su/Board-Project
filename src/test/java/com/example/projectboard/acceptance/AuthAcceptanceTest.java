package com.example.projectboard.acceptance;

import com.example.projectboard.auth.application.dto.request.AuthRequest;
import com.example.projectboard.member.application.dto.v1.request.CreateMemberRequestDto;
import com.example.projectboard.support.response.ResultType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.projectboard.acceptance.AuthSteps.로그인_요청;
import static com.example.projectboard.acceptance.MemberSteps.request;
import static com.example.projectboard.acceptance.MemberSteps.회원_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그인 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    final String name = "김지수";
    final String email = "kjs3268@gmail.com";
    final String password = "1234";

    @Test
    void 로그인() {
        // given : 선행조건 기술
        CreateMemberRequestDto memberRequest = request(name, email, password);
        회원_생성_요청(memberRequest);
        AuthRequest request = AuthSteps.request(email, password);

        // when : 기능 수행
        ExtractableResponse<Response> response = 로그인_요청(request);

        // then : 결과 확인
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("result")).isEqualTo(ResultType.SUCCESS.name());
        assertThat(response.jsonPath().getString("data.token")).isNotNull();
    }
}
