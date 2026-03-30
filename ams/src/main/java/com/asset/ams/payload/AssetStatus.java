package com.asset.ams.payload;

public enum AssetStatus {

    AVAILABLE("Available"),
    ASSIGNED("Assigned"),
    RETIRED("Retired");

    private final String value;

    AssetStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
