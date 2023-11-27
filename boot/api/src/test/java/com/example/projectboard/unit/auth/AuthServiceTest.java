package com.example.projectboard.unit.auth;


import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.auth.application.AuthService;
import com.example.projectboard.auth.application.dto.AuthDto;
import com.example.projectboard.member.MemberLevel;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import com.example.projectboard.support.error.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Auth 서비스 테스트")
class AuthServiceTest extends AcceptanceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("로그인 - 성공")
    void login() {
        // given : 선행조건 기술
        memberService.createMember(createMemberDto());

        // when : 기능 수행
        String token = authService.login(createAuthDto());

        // then : 결과 확인
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("로그인 - 실패")
    void loginReturnThroes() {
        // given : 선행조건 기술
        memberService.createMember(createMemberDto());

        // when : 기능 수행 & then : 결과 확인
        assertThatThrownBy(() -> authService.login(createNotValidAuthDto()))
                .isInstanceOf(MemberException.class)
                .hasMessage("로그인 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("회원 조회 - 성공")
    void findMember() {
        // given : 선행조건 기술
        MemberDto memberDto = memberService.createMember(createMemberDto());

        // when : 기능 수행
        MemberDto findMember = authService.findMember(memberDto.id());

        // then : 결과 확인
        assertThat(findMember).isNotNull();
        assertThat(findMember.id()).isEqualTo(memberDto.id());
        assertThat(findMember.name()).isEqualTo(memberDto.name());
        assertThat(findMember.email()).isEqualTo(memberDto.email());
        assertThat(findMember.memberLevel()).isEqualTo(memberDto.memberLevel());
    }

    private AuthDto createAuthDto() {
        return new AuthDto("jisu@email.com", "1234");
    }
    private AuthDto createNotValidAuthDto() {
        return new AuthDto("jisu@email.com", "12345");
    }

    private MemberDto createMemberDto() {
        return new MemberDto(null, "김지수", "jisu@email.com", "1234", null, MemberLevel.NORMAL);
    }
}