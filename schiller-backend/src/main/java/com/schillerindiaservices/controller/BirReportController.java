package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.BirAdminRowDTO;
import com.schillerindiaservices.service.BirService;
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
@RequestMapping("/api/bir")
@RequiredArgsConstructor
public class BirReportController {

    private static final Set<String> SORT_WHITELIST = Set.of(
            "id", "division", "entryDate", "fqcInDate", "model", "configuration", "receivedQty",
            "softwrChanges", "hardwrChanges", "accesoryDetails", "cnrRefNo", "birRefNo", "finalStatus"
    );

    private final BirService birService;

    @GetMapping("/admin/pending")
    public ResponseEntity<Page<BirAdminRowDTO>> getPending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(birService.findPendingAdmin(search, pageable(page, size, sort)));
    }

    @GetMapping("/admin/completed")
    public ResponseEntity<Page<BirAdminRowDTO>> getCompleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(birService.findCompletedAdmin(division, search, pageable(page, size, sort)));
    }

    @GetMapping("/admin/export")
    public ResponseEntity<byte[]> export(
            @RequestParam(defaultValue = "Pending") String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String division
    ) throws IOException {
        byte[] body = birService.exportAdminExcel(status, from, to, division);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"BIR.xlsx\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(body);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        birService.deleteById(id);
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
