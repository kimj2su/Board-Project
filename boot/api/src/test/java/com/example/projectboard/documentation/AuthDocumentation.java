package com.example.projectboard.documentation;

import com.example.projectboard.acceptance.AuthSteps;
import com.example.projectboard.auth.application.AuthService;
import com.example.projectboard.auth.application.dto.request.AuthRequest;
import com.example.projectboard.member.MemberLevel;
import com.example.projectboard.member.MemberRole;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.support.jwt.JwtTokenUtils;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

@DisplayName("인증 관련 문서화")
public class AuthDocumentation extends Documentation {

    @MockBean
    private AuthService authService;
    @MockBean
    private MemberService memberService;

    private final String name = "김지수";
    private final String email = "kimjisu@email.com";
    private final String password = "1234";

    private final MemberDto memberDto = memberDto();

    @DisplayName("로그인")
    @Test
    void login() {
        // given : 선행조건 기술
        AuthRequest authRequest = authRequest();
        BDDMockito.given(authService.login(any())).willReturn("token");

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("auth-login",
                        requestFields(
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.token").type(JsonFieldType.STRING).description("JWT 토큰"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        AuthSteps.로그인_요청_문서화(requestSpecification, authRequest);
    }

    @DisplayName("내정보 조회")
    @Test
    void myPage() {
        // given : 선행조건 기술
        String token = JwtTokenUtils.generateToken(email, jwtTokenProperties.getSecretKey(), jwtTokenProperties.getTokenExpiredTimeMs());
        given(memberService.loadUserByUsername(any())).willReturn(memberDto);
        given(authService.findMember(any())).willReturn(memberDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("auth-myPage",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("data.memberLevel").type(JsonFieldType.STRING).description("회원 등급"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        AuthSteps.내정보_조회_요청_문서화(requestSpecification, token);
    }

    private AuthRequest authRequest() {
        return new AuthRequest(email, password);
    }

    private MemberDto memberDto() {
        return new MemberDto(1L, name, email, password, MemberRole.USER, MemberLevel.NORMAL);
    }
}
