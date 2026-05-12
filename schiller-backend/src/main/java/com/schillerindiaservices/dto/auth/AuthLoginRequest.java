package com.schillerindiaservices.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
