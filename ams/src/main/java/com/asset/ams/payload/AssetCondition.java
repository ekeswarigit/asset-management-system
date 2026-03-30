package com.asset.ams.payload;

public enum AssetCondition {

    GOOD("Good"),
    FAIR("Fair"),
    POOR("Poor");

    private final String value;

    AssetCondition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
