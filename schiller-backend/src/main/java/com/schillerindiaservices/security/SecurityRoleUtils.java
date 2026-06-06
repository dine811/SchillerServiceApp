package com.schillerindiaservices.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * Shared role checks for legacy parity (e.g. service coordinator sees all spares divisions).
 */
public final class SecurityRoleUtils {

    private SecurityRoleUtils() {}

    public static boolean hasAnyRole(Authentication authentication, String... roles) {
        if (authentication == null) {
            return false;
        }
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String auth = authority.getAuthority();
            for (String role : roles) {
                if (("ROLE_" + role).equalsIgnoreCase(auth) || role.equalsIgnoreCase(auth)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Legacy spareslist_update.jsp: coordinator sees all pending spares (optional division filter). */
    public static boolean isSparesAllDivisionsScope(Authentication authentication) {
        return hasAnyRole(authentication, "ADMIN", "VICE_CHANCELLOR", "SERVICE_COORDINATOR");
    }
}
