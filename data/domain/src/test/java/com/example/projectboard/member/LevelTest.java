package com.example.projectboard.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level Domain 테스트")
public class LevelTest {

    @Test
    void createLevel() {
        // given : 선행조건 기술

        // when : 기능 수행
        Level level = new Level(createMember("김지수", "kimjisu3268@gmail.com", "1234"), 0, 0);

        // then : 결과 확인
        assertThat(level.getMember()).isNotNull();
        assertThat(level.getMemberLevel()).isEqualTo(MemberLevel.NORMAL);
        assertThat(level.getPostCount()).isEqualTo(0);
        assertThat(level.getLikesCount()).isEqualTo(0);
    }

    private Member createMember(String name, String email, String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
