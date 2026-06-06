package com.schillerindiaservices.controller;

import com.schillerindiaservices.security.SecurityRoleUtils;
import com.schillerindiaservices.service.SpareMasterExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/spares")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class SpareMasterExportController {

    private final SpareMasterExportService spareMasterExportService;

    @GetMapping("/export/pending")
    public ResponseEntity<byte[]> exportPending(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            Authentication authentication
    ) throws IOException {
        boolean isPrivileged = SecurityRoleUtils.isSparesAllDivisionsScope(authentication);
        byte[] excel = spareMasterExportService.exportPendingToExcel(
                authentication.getName(),
                isPrivileged,
                division,
                search,
                from,
                to
        );
        String filename = "SparesList_" + LocalDate.now() + ".xlsx";
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
        boolean isPrivileged = SecurityRoleUtils.isSparesAllDivisionsScope(authentication);
        byte[] excel = spareMasterExportService.exportCompletedToExcel(
                authentication.getName(),
                isPrivileged,
                division,
                search,
                from,
                to
        );
        String filename = "SparesListCompleted_" + LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }
}
