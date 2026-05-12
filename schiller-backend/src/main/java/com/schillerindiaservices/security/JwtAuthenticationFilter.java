package com.schillerindiaservices.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String ACCESS_COOKIE = "access_token";
    /** Must match Next.js {@code CLIENT_AUTH_COOKIE} — mirror JWT for middleware; also sent to API with credentials. */
    public static final String CLIENT_MIRROR_COOKIE = "sis_at";
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractBearer(request);
        if (token == null) {
            token = extractFromCookie(request);
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Claims claims = jwtService.parse(token);
                String username = claims.getSubject();
                String role = String.valueOf(claims.get("role"));
                String authorityRole = "ROLE_" + role;

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(authorityRole))
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {
                // Invalid/expired token -> leave unauthenticated
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractBearer(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return auth.substring(7).trim();
        }
        return null;
    }

    private String extractFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (ACCESS_COOKIE.equals(c.getName())) {
                return c.getValue();
            }
        }
        for (Cookie c : cookies) {
            if (CLIENT_MIRROR_COOKIE.equals(c.getName())) {
                String v = c.getValue();
                if (v == null || v.isBlank()) return null;
                try {
                    return URLDecoder.decode(v, StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return v;
                }
            }
        }
        return null;
    }
}
