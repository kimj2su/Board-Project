package com.example.projectboard.documentation;

import com.example.projectboard.acceptance.PostSteps;
import com.example.projectboard.config.JwtTokenProperties;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.member.domain.MemberRole;
import com.example.projectboard.post.appllication.PostService;
import com.example.projectboard.post.appllication.dto.PostDto;
import com.example.projectboard.post.appllication.dto.v1.request.CreatePostRequestDto;
import com.example.projectboard.post.appllication.dto.v1.request.ModifyPostRequestDto;
import com.example.projectboard.support.jwt.JwtTokenUtils;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

@DisplayName("게시글 관련 문서화")
class PostDocumentation extends Documentation {

    @MockBean
    private PostService postService;
    @MockBean
    private MemberService memberService;

    private final String name = "김지수";
    private final String email = "kimjisu@email.com";
    private final String password = "1234";
    private final String title = "게시글";
    private final String content = "게시글 내용";
    private String token;
    private final MemberDto memberDto = memberDto();

    @BeforeEach
    void createToken() {
        token = JwtTokenUtils.generateToken(email, jwtTokenProperties.getSecretKey(), jwtTokenProperties.getTokenExpiredTimeMs());
        given(memberService.loadMemberByEmail(any())).willReturn(memberDto);
    }

    @DisplayName("게시글 생성")
    @Test
    void createPost() {
        // given : 선행조건 기술
        PostDto postDto = postDto();
        CreatePostRequestDto request = PostSteps.request(title, content);
        given(postService.createPost(any())).willReturn(postDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("post-create",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("게시글 아이디"),
                                fieldWithPath("data.title").description("제목"),
                                fieldWithPath("data.content").description("내용"),
                                fieldWithPath("data.memberResponse.id").description("작성자 아이디"),
                                fieldWithPath("data.memberResponse.name").description("작성자 이름"),
                                fieldWithPath("data.memberResponse.email").description("작성자 이메일"),
                                fieldWithPath("error").description("에러 내용")
                        )
                ));

        // then : 결과 확인
        PostSteps.게시글_생성_요청_문서화(token, request, requestSpecification);
    }

    @DisplayName("게시글 조회")
    @Test
    void findPost() {
        // given : 선행조건 기술
        PostDto postDto = postDto();
        given(postService.findPost(any())).willReturn(postDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("post-find",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("게시글 아이디"),
                                fieldWithPath("data.title").description("제목"),
                                fieldWithPath("data.content").description("내용"),
                                fieldWithPath("data.memberResponse.id").description("작성자 아이디"),
                                fieldWithPath("data.memberResponse.name").description("작성자 이름"),
                                fieldWithPath("data.memberResponse.email").description("작성자 이메일"),
                                fieldWithPath("error").description("에러 내용")
                        )
                ));

        // then : 결과 확인
        PostSteps.게시글_조회_요청_문서화(token, requestSpecification);
    }

    @DisplayName("게시글 수정")
    @Test
    void modifyPost() {
        // given : 선행조건 기술
        PostDto postDto = postDto();
        ModifyPostRequestDto request = PostSteps.modifyRequest(title, content);
        given(postService.createPost(any())).willReturn(postDto);

        // when : 기능 수행
        RequestSpecification requestSpecification = RestAssured.given(spec).log().all()
                .filter(document("post-modify",
                        requestHeaders(
                                headerWithName("authorization").description("JWT 인증 토큰")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("result").description("결과"),
                                fieldWithPath("data").description("응답 데이터"),
                                fieldWithPath("data.id").description("게시글 아이디"),
                                fieldWithPath("data.title").description("제목"),
                                fieldWithPath("data.content").description("내용"),
                                fieldWithPath("data.memberResponse.id").description("작성자 아이디"),
                                fieldWithPath("data.memberResponse.name").description("작성자 이름"),
                                fieldWithPath("data.memberResponse.email").description("작성자 이메일"),
                                fieldWithPath("error").description("에러 내용")
                        )
                ));

        // then : 결과 확인
        PostSteps.게시글_생성_요청_문서화(token, request, requestSpecification);
    }

    private PostDto postDto() {
        return new PostDto(1L, memberDto, "내용", "작성자");
    }

    private MemberDto memberDto() {
        return new MemberDto(1L, name, email, password, MemberRole.USER);
    }
}
