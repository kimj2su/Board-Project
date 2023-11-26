package com.example.projectboard.member;

import java.util.Objects;

public enum MemberLevel {

    VIP(20, null),
    GOLD(15, VIP),
    SILVER(10, GOLD),
    NORMAL(5, SILVER);

    private final int nextPoint;
    private final MemberLevel nextMemberLevel;

    MemberLevel(int nextPoint, MemberLevel nextMemberLevel) {
        this.nextPoint = nextPoint;
        this.nextMemberLevel = nextMemberLevel;
    }

    public static MemberLevel getNextLevel(int nextPoint) {
        if (nextPoint >= MemberLevel.VIP.nextPoint) {
            return VIP;
        }

        if (nextPoint >= MemberLevel.GOLD.nextPoint) {
            return GOLD.nextMemberLevel;
        }

        if (nextPoint >= MemberLevel.SILVER.nextPoint) {
            return SILVER.nextMemberLevel;
        }

        if (nextPoint >= MemberLevel.NORMAL.nextPoint) {
            return NORMAL.nextMemberLevel;
        }

        return NORMAL;
    }

    public static boolean availableLevelUp(MemberLevel memberLevel, int totalPoint) {
        if (Objects.isNull(memberLevel)) {
            return false;
        }

        if (Objects.isNull(memberLevel.nextMemberLevel)) {
            return false;
        }


        return totalPoint >= memberLevel.nextPoint;
    }

    public int getNextPoint() {
        return nextPoint;
    }

    public MemberLevel getNextLevel() {
        return nextMemberLevel;
    }
}
