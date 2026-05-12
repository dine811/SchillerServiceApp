package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.PrfobAdminRowDTO;
import com.schillerindiaservices.service.PrfobService;
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

/**
 * PRF/OB admin — legacy PRFOB_AdminList.jsp (pending), PRFOB_AdminList_closed.jsp (completed), export.
 */
@RestController
@RequestMapping("/api/prf-ob")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class PrfobController {

    private static final Set<String> SORT_WHITELIST = Set.of(
            "id", "division", "entryDate", "scEngg", "workType", "raisedDate", "receivedDate",
            "region", "branch", "engineer", "dealer", "model", "supplier", "warrentyStatus",
            "prfobRefNo", "crmRefNo", "statusType", "executedDate", "partType", "partDescription"
    );

    private final PrfobService prfobService;

    @GetMapping("/admin/pending")
    public ResponseEntity<Page<PrfobAdminRowDTO>> getPending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(prfobService.findPendingAdmin(division, search, pageable(page, size, sort)));
    }

    @GetMapping("/admin/completed")
    public ResponseEntity<Page<PrfobAdminRowDTO>> getCompleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(prfobService.findCompletedAdmin(division, search, pageable(page, size, sort)));
    }

    @GetMapping("/admin/export")
    public ResponseEntity<byte[]> exportAdmin(
            @RequestParam(defaultValue = "Pending") String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String division
    ) throws IOException {
        byte[] body = prfobService.exportAdminExcel(status, from, to, division);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"PRF-OB.xlsx\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(body);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        prfobService.deleteById(id);
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
