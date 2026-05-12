package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.auth.AuthLoginRequest;
import com.schillerindiaservices.dto.auth.AuthResponse;
import com.schillerindiaservices.security.JwtAuthenticationFilter;
import com.schillerindiaservices.security.JwtService;
import com.schillerindiaservices.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        final AuthResponse auth;
        try {
            auth = authService.login(request.getUsername(), request.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ResponseCookie cookie = ResponseCookie.from(JwtAuthenticationFilter.ACCESS_COOKIE, auth.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(auth);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie clear = ResponseCookie.from(JwtAuthenticationFilter.ACCESS_COOKIE, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, clear.toString())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse.UserProfile> me(@CookieValue(name = JwtAuthenticationFilter.ACCESS_COOKIE, required = false) String cookieToken,
                                                       @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7).trim();
        } else if (cookieToken != null && !cookieToken.isBlank()) {
            token = cookieToken;
        }
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            Claims c = jwtService.parse(token);
            AuthResponse.UserProfile profile = new AuthResponse.UserProfile(
                    c.get("empId", Number.class).longValue(),
                    c.getSubject(),
                    c.get("name", String.class),
                    c.get("role", String.class),
                    c.get("division", String.class)
            );
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
