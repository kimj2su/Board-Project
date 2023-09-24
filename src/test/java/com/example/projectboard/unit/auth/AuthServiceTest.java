package com.example.projectboard.unit.auth;


import com.example.projectboard.acceptance.AcceptanceTest;
import com.example.projectboard.auth.application.AuthService;
import com.example.projectboard.auth.application.dto.AuthDto;
import com.example.projectboard.member.application.MemberService;
import com.example.projectboard.member.application.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Auth 서비스 테스트")
class AuthServiceTest extends AcceptanceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private MemberService memberService;

    @Test
    void login() {
        // given : 선행조건 기술
        memberService.createMember(createMemberDto());

        // when : 기능 수행
        String token = authService.login(createAuthDto());

        // then : 결과 확인
        assertThat(token).isNotNull();
    }

    private AuthDto createAuthDto() {
        return new AuthDto("jisu@email.com", "1234");
    }

    private MemberDto createMemberDto() {
        return new MemberDto(null, "김지수", "jisu@email.com", "1234", null);
    }
}