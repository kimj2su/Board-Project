package com.example.projectboard.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member 테스트")
public class MemberTest {

    private final String name = "김지수";
    private final String email = "kimjisu3268@gmail.com";
    private final String password = "1234";

    @DisplayName("멤버 생성 테스트")
    @Test
    void createMemte() {
        // given : 선행조건 기술


        // when : 기능 수행
        Member member = createMember(name, email, password);

        // then : 결과 확인
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.USER);
        assertThat(member.getLevel()).isEqualTo(Level.NORMAL);
    }

    @DisplayName("멤버 수정 테스트")
    @Test
    void modifyMember() {
        // given : 선행조건 기술
        Member member = createMember(name, email, password);
        String modifyName = "김지수2";
        String modifyEmail = "kimjisu3268@gmail.com2";

        // when : 기능 수행
        member.modify(modifyName, modifyEmail);

        // then : 결과 확인
        assertThat(member.getName()).isEqualTo(modifyName);
        assertThat(member.getEmail()).isEqualTo(modifyEmail);
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.USER);
        assertThat(member.getLevel()).isEqualTo(Level.NORMAL);
    }

    private Member createMember(String name, String email, String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
