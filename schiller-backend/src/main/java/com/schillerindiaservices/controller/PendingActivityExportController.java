package com.schillerindiaservices.controller;

import com.schillerindiaservices.service.PendingActivityExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.schillerindiaservices.security.SecurityRoleUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/pending-activity")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class PendingActivityExportController {

    private final PendingActivityExportService pendingActivityExportService;

    @GetMapping("/export/pending")
    public ResponseEntity<byte[]> exportPending(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            Authentication authentication
    ) throws IOException {
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        byte[] excel = pendingActivityExportService.exportPendingToExcel(
                authentication.getName(),
                allDivisions,
                division,
                search,
                from,
                to
        );
        String filename = "PendingActivity_" + LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export/completed")
    public ResponseEntity<byte[]> exportCompleted(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            Authentication authentication
    ) throws IOException {
        boolean isPrivileged = authentication.getAuthorities().stream().anyMatch(a -> {
            String auth = a.getAuthority();
            return "ROLE_ADMIN".equalsIgnoreCase(auth) || "ROLE_VICE_CHANCELLOR".equalsIgnoreCase(auth);
        });
        byte[] excel = pendingActivityExportService.exportCompletedToExcel(
                authentication.getName(),
                isPrivileged,
                division,
                search,
                from,
                to
        );
        String filename = "ClosedActivity_" + LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }
}
