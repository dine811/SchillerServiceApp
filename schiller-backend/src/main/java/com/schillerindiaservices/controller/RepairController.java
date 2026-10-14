package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.RepairDTO;
import com.schillerindiaservices.dto.RepairUpdateRequest;
import com.schillerindiaservices.security.SecurityRoleUtils;
import com.schillerindiaservices.service.RepairExportService;
import com.schillerindiaservices.service.RepairService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api/repairs")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class RepairController {
    private static final Set<String> SORT_FIELDS = Set.of("id", "entryDate", "division", "scRefNo", "defGirNo",
            "category", "model", "defBrdModName", "finishedDate", "repairedBy", "status", "noOfDays");

    private final RepairService repairService;
    private final RepairExportService repairExportService;

    @GetMapping
    public ResponseEntity<Page<RepairDTO>> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String scRefNo,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort) {
        return ResponseEntity.ok(repairService.findAll(category, status, from, to, division, scRefNo, search,
                pageable(page, size, sort)));
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<RepairDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(repairService.findById(id));
    }

    @PostMapping("/from-service/{serviceId:\\d+}")
    public ResponseEntity<RepairDTO> initiate(@PathVariable Long serviceId,
                                               @RequestParam String category,
                                               Authentication authentication) {
        boolean admin = SecurityRoleUtils.hasAnyRole(authentication, "ADMIN");
        RepairDTO created = repairService.createFromService(serviceId, category, authentication.getName(), admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<RepairDTO> update(@PathVariable Integer id,
                                             @Valid @RequestBody RepairUpdateRequest request) {
        return ResponseEntity.ok(repairService.update(id, request));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String scRefNo,
            @RequestParam(required = false) String search) throws IOException {
        byte[] body = repairExportService.export(category, status, from, to, division, scRefNo, search);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RepairList_" + LocalDate.now() + ".xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(body);
    }

    private static Pageable pageable(int page, int size, String sort) {
        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page must be >= 0 and size must be 1..100");
        }
        String[] parts = sort == null ? new String[0] : sort.split(",", -1);
        String property = parts.length == 0 || parts[0].isBlank() ? "id" : parts[0].trim();
        if (!SORT_FIELDS.contains(property)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported repair sort field");
        }
        if (parts.length > 2 || (parts.length == 2
                && !parts[1].equalsIgnoreCase("asc") && !parts[1].equalsIgnoreCase("desc"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort must be property,asc or property,desc");
        }
        Sort.Direction direction = parts.length == 2 && parts[1].equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort requested = Sort.by(direction, property);
        if (!"id".equals(property)) requested = requested.and(Sort.by(direction, "id"));
        return PageRequest.of(page, size, requested);
    }
}
