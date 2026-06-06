package com.schillerindiaservices.controller;

import com.schillerindiaservices.service.ServiceExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.schillerindiaservices.security.SecurityRoleUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

/**
 * REST controller for exporting Service Master list to Excel.
 * Separate from ServiceMasterController as per migration requirements.
 */
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceExportController {

    private final ServiceExportService serviceExportService;

    @GetMapping("/export/under-repair")
    public ResponseEntity<byte[]> exportUnderRepair(
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search
    ) throws IOException {
        byte[] excel = serviceExportService.exportUnderRepairToExcel(scRef, search);
        String filename = "UnderRepair_" + java.time.LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export/pending-frn")
    public ResponseEntity<byte[]> exportPendingFrn(
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search,
            Authentication authentication
    ) throws IOException {
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        byte[] excel = serviceExportService.exportPendingFrnToExcel(
                scRef, search, authentication.getName(), allDivisions);
        String filename = "PendingFRN_" + java.time.LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export/ob-pending")
    public ResponseEntity<byte[]> exportObPending(
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search
    ) throws IOException {
        byte[] excel = serviceExportService.exportObPendingToExcel(scRef, search);
        String filename = "OBPending_" + java.time.LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export/sc-closed")
    public ResponseEntity<byte[]> exportScClosed(
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search
    ) throws IOException {
        byte[] excel = serviceExportService.exportScClosedProductToExcel(scRef, search);
        String filename = "SC_ClosedProduct_" + java.time.LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export/new-closed")
    public ResponseEntity<byte[]> exportNewClosed(
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search
    ) throws IOException {
        byte[] excel = serviceExportService.exportNewClosedProductToExcel(scRef, search);
        String filename = "ClosedProduct_" + java.time.LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export/scrap-list")
    public ResponseEntity<byte[]> exportScrapList(
            @RequestParam(required = false) String division,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String scRef,
            @RequestParam(required = false) String search
    ) throws IOException {
        byte[] excel = serviceExportService.exportScrapListToExcel(division, month, year, scRef, search);
        String filename = "ScrapList_" + LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String search,
            Authentication authentication
    ) throws IOException {
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        byte[] excel = serviceExportService.exportToExcelForUser(from, to, search, authentication.getName(), allDivisions);

        String filename = "ServiceList_" + LocalDate.now() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }
}
