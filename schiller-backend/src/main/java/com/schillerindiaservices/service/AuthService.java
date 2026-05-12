package com.schillerindiaservices.service;

import com.schillerindiaservices.dto.auth.AuthResponse;
import com.schillerindiaservices.entity.Employee;
import com.schillerindiaservices.repository.EmployeeRepository;
import com.schillerindiaservices.security.AuthRole;
import com.schillerindiaservices.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public AuthResponse login(String username, String password) {
        Employee emp = employeeRepository.findByUsername(username)
                .or(() -> employeeRepository.findByUsernameIgnoreCase(username))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!Boolean.TRUE.equals(emp.getActive())) {
            throw new BadCredentialsException("User is inactive");
        }

        // Phase-1: plain password validation (as requested for speed). Replace with BCrypt in phase-2.
        if (emp.getPassword() == null || !emp.getPassword().equals(password)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        AuthRole normalizedRole = normalizeRole(emp.getRole());
        Map<String, Object> claims = new HashMap<>();
        claims.put("empId", emp.getEmpId());
        claims.put("name", emp.getEmpName());
        claims.put("role", normalizedRole.name());
        claims.put("division", emp.getDivision());

        String token = jwtService.generateToken(emp.getUsername(), claims);
        return new AuthResponse(
                token,
                new AuthResponse.UserProfile(
                        emp.getEmpId(),
                        emp.getUsername(),
                        emp.getEmpName(),
                        normalizedRole.name(),
                        emp.getDivision()
                )
        );
    }

    public static AuthRole normalizeRole(String rawRole) {
        return AuthRole.fromRaw(rawRole);
    }
}
