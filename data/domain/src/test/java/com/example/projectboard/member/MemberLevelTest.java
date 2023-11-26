package com.example.projectboard.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Level 테스트")
public class MemberLevelTest {

    @DisplayName("Level 생성 테스트")
    @Test
    void createLevel() {
        // given : 선행조건 기술
        MemberLevel memberLevel = MemberLevel.NORMAL;

        // when : 기능 수행
        MemberLevel memberLevel1 = MemberLevel.valueOf(memberLevel.name());

        // then : 결과 확인
        assertThat(memberLevel).isEqualTo(memberLevel1);
    }

    @DisplayName("Level 다음 레벨 조회 테스트 - NORMAL")
    @Test
    void getNextLevelWithNORMAL() {
        // given : 선행조건 기술
        MemberLevel memberLevel = MemberLevel.NORMAL;

        // when : 기능 수행
        MemberLevel nextMemberLevel = MemberLevel.getNextLevel(5);

        // then : 결과 확인
        assertThat(nextMemberLevel).isEqualTo(MemberLevel.SILVER);
    }

    @DisplayName("Level 다음 레벨 조회 테스트 - VIP")
    @Test
    void getNextLevelWithVIP() {
        // given : 선행조건 기술
        MemberLevel memberLevel = MemberLevel.VIP;

        // when : 기능 수행
        MemberLevel nextMemberLevel = MemberLevel.getNextLevel(30);

        // then : 결과 확인
        assertThat(nextMemberLevel).isEqualTo(MemberLevel.VIP);
    }

    @DisplayName("Level 다음 레벨이 있는지 확인 테스")
    @Test
    void availableLevelUpWithNORMAL() {
        // given : 선행조건 기술
        MemberLevel memberLevel = MemberLevel.NORMAL;

        // when : 기능 수행
        boolean isLevelUp = MemberLevel.availableLevelUp(memberLevel, 10);

        // then : 결과 확인
        assertThat(isLevelUp).isTrue();
    }

    @DisplayName("Level 다음 레벨이 있는지 확인 테스트 - 경계값")
    @Test
    void availableLevelUpWithVIP() {
        // given : 선행조건 기술
        MemberLevel memberLevel = MemberLevel.VIP;

        // when : 기능 수행
        boolean isLevelUp = MemberLevel.availableLevelUp(memberLevel, 30);

        // then : 결과 확인
        assertThat(isLevelUp).isFalse();
    }


}
