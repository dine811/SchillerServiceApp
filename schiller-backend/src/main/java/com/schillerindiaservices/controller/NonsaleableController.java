package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.NonsaleableAdminRowDTO;
import com.schillerindiaservices.service.NonsaleableService;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api/nonsaleable")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class NonsaleableController {

    private static final Set<String> SORT_WHITELIST = Set.of(
            "id", "division", "entryDate", "unitDetails", "fqcInDate", "model", "fqcObservation",
            "scInwardDate", "scObservation", "modelSn", "tentDateShipDate", "shipDateFqc", "finalStatus"
    );

    private final NonsaleableService nonsaleableService;

    @GetMapping("/admin/pending")
    public ResponseEntity<Page<NonsaleableAdminRowDTO>> getPending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(nonsaleableService.findPendingAdmin(search, pageable(page, size, sort)));
    }

    @GetMapping("/salables")
    public ResponseEntity<Page<NonsaleableAdminRowDTO>> getSalables(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(nonsaleableService.findSalables(division, search, pageable(page, size, sort)));
    }

    @GetMapping("/admin/export")
    public ResponseEntity<byte[]> export(
            @RequestParam(defaultValue = "Pending") String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String division
    ) throws IOException {
        byte[] body = nonsaleableService.exportAdminExcel(status, from, to, division);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Non-Salable.xlsx\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(body);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        nonsaleableService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private static Pageable pageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "id" : sortParams[0].trim();
        if (!SORT_WHITELIST.contains(prop)) {
            prop = "id";
        }
        return PageRequest.of(page, size, Sort.by(direction, prop));
    }
}
