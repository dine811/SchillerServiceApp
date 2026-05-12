package com.schillerindiaservices.security;

import java.util.Locale;

public enum AuthRole {
    ADMIN,
    ENGINEER,
    FQC,
    VICE_CHANCELLOR,
    SERVICE_COORDINATOR,
    REPAIR_TEAM,
    PRODUCT_SUPPORT;

    public static AuthRole fromRaw(String rawRole) {
        if (rawRole == null || rawRole.isBlank()) {
            return PRODUCT_SUPPORT;
        }
        String role = rawRole.trim().toLowerCase(Locale.ROOT).replace(' ', '_');
        return switch (role) {
            case "admin", "administrator", "super_admin", "superadmin" -> ADMIN;
            case "engineer" -> ENGINEER;
            case "fqc" -> FQC;
            case "vicechancellor", "vice_chancellor", "vice chancellor" -> VICE_CHANCELLOR;
            case "servicecoordinator", "service_coordinator", "service coordinator" -> SERVICE_COORDINATOR;
            case "repairteam", "repair_team", "repair team" -> REPAIR_TEAM;
            case "productsupport", "product_support", "product support", "ps" -> PRODUCT_SUPPORT;
            default -> PRODUCT_SUPPORT;
        };
    }
}
