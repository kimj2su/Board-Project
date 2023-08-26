package com.example.projectboard.documentation;

import com.example.projectboard.acceptance.MemberSteps;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.application.dto.v1.request.CreateMemberRequestDto;
import com.example.projectboard.member.application.dto.v1.request.ModifyMemberRequestDto;
import com.example.projectboard.member.domain.MemberRole;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

@DisplayName("회원 관련 문서화")
public class MemberDocumentation extends Documentation {

    @MockBean
    private MemberService memberService;

    final String name = "김지수";
    final String email = "kimjisu@email.com";
    final String password = "1234";

    @DisplayName("회원 조회")
    @Test
    void findMember() {
        // given : 선행조건 기술
        MemberDto memberDto = memberDto();
        BDDMockito.given(memberService.findMember(1L)).willReturn(memberDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("member-find",
                        pathParameters(
                                parameterWithName("id").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러 내용")
                        )
                ));

        // then : 결과 확인
        MemberSteps.회원_조회_요청_문서화(1L, requestSpecification);
    }

    @DisplayName("회원 생성")
    @Test
    void createMember() {
        // given : 선행조건 기술
        MemberDto memberDto = memberDto();
        CreateMemberRequestDto request = new CreateMemberRequestDto(name, email, password);
        BDDMockito.given(memberService.createMember(any())).willReturn(memberDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("member-create",
                        requestFields(
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러 내용")
                        )
                ));

        // then : 결과 확인
        MemberSteps.회원_생성_요청_문서화(requestSpecification, request);
    }

    @DisplayName("회원 수정")
    @Test
    void modifyMember() {
        // given : 선행조건 기술
        ModifyMemberRequestDto modifyMemberRequest = new ModifyMemberRequestDto(name, email);
        BDDMockito.willDoNothing().given(memberService).modifyMember(anyLong(), any());

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("member-modify",
                        requestFields(
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("email").description("회원 이메일")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러 내용")
                        )
                ));

        // then : 결과 확인
        MemberSteps.회원_수정_요청_문서화(requestSpecification, modifyMemberRequest);
    }

    @DisplayName("회원 삭제")
    @Test
    void deleteMember() {
        // given : 선행조건 기술
        BDDMockito.willDoNothing().given(memberService).deleteMember(anyLong());

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("member-delete",
                        pathParameters(
                                parameterWithName("id").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러 내용")
                        )
                ));

        // then : 결과 확인
        MemberSteps.회원_삭제_요청_문서화(requestSpecification);
    }

    private MemberDto memberDto() {
        return new MemberDto(1L, name, email, password, MemberRole.USER);
    }
}
