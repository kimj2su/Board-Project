package com.example.projectboard.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 테스트")
public class LevelTest {

    @DisplayName("Level 생성 테스트")
    @Test
    void createLevel() {
        // given : 선행조건 기술
        Level level = Level.NORMAL;

        // when : 기능 수행
        Level level1 = Level.valueOf(level.name());

        // then : 결과 확인
        assertThat(level).isEqualTo(level1);
    }

    @DisplayName("Level 다음 레벨 조회 테스트 - NORMAL")
    @Test
    void getNextLevelWithNORMAL() {
        // given : 선행조건 기술
        Level level = Level.NORMAL;

        // when : 기능 수행
        Level nextLevel = Level.getNextLevel(5);

        // then : 결과 확인
        assertThat(nextLevel).isEqualTo(Level.SILVER);
    }

    @DisplayName("Level 다음 레벨 조회 테스트 - VIP")
    @Test
    void getNextLevelWithVIP() {
        // given : 선행조건 기술
        Level level = Level.VIP;

        // when : 기능 수행
        Level nextLevel = Level.getNextLevel(30);

        // then : 결과 확인
        assertThat(nextLevel).isEqualTo(Level.VIP);
    }

    @DisplayName("Level 다음 레벨이 있는지 확인 테스")
    @Test
    void availableLevelUpWithNORMAL() {
        // given : 선행조건 기술
        Level level = Level.NORMAL;

        // when : 기능 수행
        boolean isLevelUp = Level.availableLevelUp(level, 10);

        // then : 결과 확인
        assertThat(isLevelUp).isTrue();
    }

    @DisplayName("Level 다음 레벨이 있는지 확인 테스트 - 경계값")
    @Test
    void availableLevelUpWithVIP() {
        // given : 선행조건 기술
        Level level = Level.VIP;

        // when : 기능 수행
        boolean isLevelUp = Level.availableLevelUp(level, 30);

        // then : 결과 확인
        assertThat(isLevelUp).isFalse();
    }


}
