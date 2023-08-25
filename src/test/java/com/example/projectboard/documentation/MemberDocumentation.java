package com.example.projectboard.documentation;

import com.example.projectboard.acceptance.MemberSteps;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.MemberRole;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

public class MemberDocumentation extends Documentation {

    @MockBean
    private MemberService memberService;

    @Test
    void findMember() {
        // given : 선행조건 기술
        MemberDto memberDto = memberDto();
        BDDMockito.given(memberService.findMember(1L)).willReturn(memberDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("member",
                        pathParameters(
                                parameterWithName("id").description("회원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("data.id").description("회원 아이디"),
                                fieldWithPath("data.name").description("회원 이름"),
                                fieldWithPath("data.email").description("회원 이메일"),
                                fieldWithPath("error").description("에러")
                        )
                ));

        // then : 결과 확인
        MemberSteps.회원_조회_요청_문서화(1L, requestSpecification);
    }

    private MemberDto memberDto() {
        return new MemberDto(1L, "김지수", "kimjisu@email.com", "1234", MemberRole.USER);
    }
}
