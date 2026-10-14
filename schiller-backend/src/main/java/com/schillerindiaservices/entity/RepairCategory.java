package com.schillerindiaservices.entity;

import java.util.Locale;

public enum RepairCategory {
    PFRN, OB, UR;

    public static RepairCategory from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Repair category is required");
        }
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Repair category must be PFRN, OB, or UR");
        }
    }
}
