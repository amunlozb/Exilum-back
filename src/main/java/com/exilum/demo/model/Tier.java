package com.exilum.demo.model;

public enum Tier {
    WHITE(1, 5),
    YELLOW(6, 10),
    RED(11, 16),
    T17(17, 17),
    OTHER(0, 0);

    private final int minTier;
    private final int maxTier;

    Tier(int minTier, int maxTier) {
        this.minTier = minTier;
        this.maxTier = maxTier;
    }

    public int getMinTier() {
        return minTier;
    }

    public int getMaxTier() {
        return maxTier;
    }
}

