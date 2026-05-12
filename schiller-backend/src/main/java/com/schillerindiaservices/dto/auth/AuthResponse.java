package com.schillerindiaservices.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private UserProfile user;

    @Data
    @AllArgsConstructor
    public static class UserProfile {
        private Long empId;
        private String username;
        private String name;
        private String role;
        private String division;
    }
}
