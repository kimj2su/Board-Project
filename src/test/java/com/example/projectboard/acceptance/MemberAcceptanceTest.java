package com.example.projectboard.acceptance;


import com.example.projectboard.member.application.dto.v1.request.CreateMemberRequestDto;
import com.example.projectboard.member.application.dto.v1.request.ModifyMemberRequestDto;
import com.example.projectboard.support.response.ResultType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


import static com.example.projectboard.acceptance.MemberSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("회원 관련 기능")
public class MemberAcceptanceTest extends AcceptanceTest {

    final String name = "김지수";
    final String email = "kjs3268@gmail.com";
    final String password = "1234";
    /**
     * <pre>
     * Feature: 회원 생성
     * Given : 이름, 이메일, 비밀번호를 입력한다.
     * When : 회원 가입 요청을 보내면
     * Then : 회원이 생성된다.
     * </pre>
     */
    @DisplayName("회원 생성")
    @Test
    void createMember() {
        // given : 선행조건 기술
        CreateMemberRequestDto request = request(name, email, password);

        // when : 기능 수행
        ExtractableResponse<Response> response = 회원_생성_요청(request);

        // then : 결과 확인
        회원생성검증(response, name, email);
    }

    /**
     * <pre>
     * Feature: 회원 조회
     * Given : 회원이 생성되어 있다.
     * When : 회원 조회 요청을 보내면
     * Then : 회원이 조회된다.
     * </pre>
     */
    @DisplayName("회원 조회")
    @Test
    void findMember() {
        // given : 선행조건 기술
        CreateMemberRequestDto request = request(name, email, password);
        회원_생성_요청(request);

        // when : 기능 수행
        ExtractableResponse<Response> response = 회원_조회_요청(1L);

        // then : 결과 확인
        회원조회검증(response, name, email);
    }

    /**
     * <pre>
     * Feature: 회원 수정
     * Given : 회원이 생성되어 있다.
     * When : 회원 수정 요청을 보내면
     * Then : 회원이 수정된다.
     * </pre>
     */
    @DisplayName("회원 수정")
    @Test
    void modifyMember() {
        // given : 선행조건 기술
        회원_생성_요청(request(name, email, password));
        String modifyName = "김지수2";
        String modifyEmail = "email@email.com";

        ModifyMemberRequestDto request = request(modifyName, modifyEmail);

        // when : 기능 수행
        ExtractableResponse<Response> response = 회원_수정_요청(request);

        // then : 결과 확인
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        회원수정검증(modifyName, modifyEmail);
    }

    /**
     * <pre>
     * Feature: 회원 삭제
     * Given : 회원이 생성되어 있다.
     * When : 회원 삭제 요청을 보내면
     * Then : 회원이 삭제된다.
     * </pre>
     */
    @DisplayName("회원 삭제")
    @Test
    void deleteMember() {
        // given : 선행조건 기술
        회원_생성_요청(request(name, email, password));

        // when : 기능 수행
        ExtractableResponse<Response> response = 회원_삭제_요청(1L);

        // then : 결과 확인
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 회원생성검증(ExtractableResponse<Response> response, String name, String email) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("result")).contains(ResultType.SUCCESS.name());
        assertThat(response.jsonPath().getMap("data")).hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("name", name)
                .containsEntry("email", email);
    }

    private void 회원조회검증(ExtractableResponse<Response> response, String name, String email) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("result")).contains(ResultType.SUCCESS.name());
        assertThat(response.jsonPath().getMap("data")).hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("name", name)
                .containsEntry("email", email);
    }

    private void 회원수정검증(String name, String email) {
        ExtractableResponse<Response> response = 회원_조회_요청(1L);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("result")).contains(ResultType.SUCCESS.name());
        assertThat(response.jsonPath().getMap("data")).hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("name", name)
                .containsEntry("email", email);
    }
}
