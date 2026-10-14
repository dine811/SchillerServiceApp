package com.schillerindiaservices.entity;

import java.util.Locale;

public enum RepairStatus {
    PENDING("pending"), COMPLETED("completed");

    private final String databaseValue;

    RepairStatus(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    public String databaseValue() {
        return databaseValue;
    }

    public static RepairStatus from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Repair status is required");
        }
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Repair status must be pending or completed");
        }
    }
}
