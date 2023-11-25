package com.example.projectboard.member;

import java.util.Objects;

public enum Level {

    VIP(20, null),
    GOLD(15, VIP),
    SILVER(10, GOLD),
    NORMAL(5, SILVER);

    private final int nextPoint;
    private final Level nextLevel;

    Level(int nextPoint, Level nextLevel) {
        this.nextPoint = nextPoint;
        this.nextLevel = nextLevel;
    }

    public static Level getNextLevel(int nextPoint) {
        if (nextPoint >= Level.VIP.nextPoint) {
            return VIP;
        }

        if (nextPoint >= Level.GOLD.nextPoint) {
            return GOLD.nextLevel;
        }

        if (nextPoint >= Level.SILVER.nextPoint) {
            return SILVER.nextLevel;
        }

        if (nextPoint >= Level.NORMAL.nextPoint) {
            return NORMAL.nextLevel;
        }

        return NORMAL;
    }

    public static boolean availableLevelUp(Level level, int totalPoint) {
        if (Objects.isNull(level)) {
            return false;
        }

        if (Objects.isNull(level.nextLevel)) {
            return false;
        }


        return totalPoint >= level.nextPoint;
    }

    public int getNextPoint() {
        return nextPoint;
    }

    public Level getNextLevel() {
        return nextLevel;
    }
}
