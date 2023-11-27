package com.example.projectboard.documentation;

import com.example.projectboard.acceptance.LikeSteps;
import com.example.projectboard.member.MemberLevel;
import com.example.projectboard.member.MemberRole;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.post.appllication.LikeService;
import com.example.projectboard.support.jwt.JwtTokenUtils;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

@DisplayName("좋아요 관련 문서화")
public class LikeDocumentation extends Documentation {

    @MockBean
    private LikeService likeService;
    @MockBean
    private MemberService memberService;

    private final String name = "김지수";
    private final String email = "kimjisu@email.com";
    private final String password = "1234";
    private String token;
    private final MemberDto memberDto = memberDto();

    @BeforeEach
    void createToken() {
        token = JwtTokenUtils.generateToken(email, jwtTokenProperties.getSecretKey(), jwtTokenProperties.getTokenExpiredTimeMs());
        given(memberService.loadUserByUsername(any())).willReturn(memberDto);
    }

    @DisplayName("좋아요 증가 문서화")
    @Test
    void increaseLike() {
        // given : 선행조건 기술
        willDoNothing().given(likeService).increase(any(), any());

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("like-increase",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.NULL).optional().description("응답 데이터"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        LikeSteps.좋아요_증가_요청_문서화(token, requestSpecification);
    }

    @DisplayName("좋아요 감소 문서화")
    @Test
    void decreaseLike() {
        // given : 선행조건 기술
        willDoNothing().given(likeService).decrease(any(), any());

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("like-decrease",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.NULL).optional().description("응답 데이터"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        LikeSteps.좋아요_감소_요청_문서화 (token, requestSpecification);
    }

    @DisplayName("좋아요 증가 문서화")
    @Test
    void toggleLike() {
        // given : 선행조건 기술
        willDoNothing().given(likeService).increase(any(), any());

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("like-toggleLike",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).optional().description("응답 데이터"),
                                fieldWithPath("data.isLike").type(JsonFieldType.BOOLEAN).description("좋아요 상태"),
                                fieldWithPath("error").type(JsonFieldType.NULL).optional().description("에러 내용")
                        )
                ));

        // then : 결과 확인
        LikeSteps.좋아요_상태_확인_문서화(token, requestSpecification);
    }

    private MemberDto memberDto() {
        return new MemberDto(1L, name, email, password, MemberRole.USER, MemberLevel.NORMAL);
    }
}
