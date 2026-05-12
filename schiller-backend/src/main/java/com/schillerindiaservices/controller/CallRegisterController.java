package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.CallRegisterDTO;
import com.schillerindiaservices.dto.CallRegisterUpdateRequest;
import com.schillerindiaservices.service.CallRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Call register — legacy CallListAdmin.jsp (pending) and ClosedCalls.jsp (completed).
 */
@RestController
@RequestMapping("/api/call-register")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class CallRegisterController {

    private final CallRegisterService callRegisterService;

    @GetMapping("/pending")
    public ResponseEntity<Page<CallRegisterDTO>> getPending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort,
            Authentication authentication
    ) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equalsIgnoreCase(a.getAuthority()));
        return ResponseEntity.ok(callRegisterService.findPendingForUser(
                authentication.getName(), isAdmin, division, search, pageable(page, size, sort)));
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<CallRegisterDTO>> getCompleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "id,desc") String sort,
            Authentication authentication
    ) {
        boolean isPrivileged = authentication.getAuthorities().stream().anyMatch(a -> {
            String auth = a.getAuthority();
            return "ROLE_ADMIN".equalsIgnoreCase(auth) || "ROLE_VICE_CHANCELLOR".equalsIgnoreCase(auth);
        });
        return ResponseEntity.ok(
                callRegisterService.findCompletedForUser(
                        authentication.getName(), isPrivileged, division, search, from, to, pageable(page, size, sort)));
    }

    /** Total / pending / completed row counts — helps explain an empty closed-calls grid. */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> summary() {
        return ResponseEntity.ok(callRegisterService.summary());
    }

    private static Pageable pageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "id" : sortParams[0];
        return PageRequest.of(page, size, Sort.by(direction, prop));
    }

    /** Single row — {@code callregister_demo} first, then {@code callregister} (legacy edit form). */
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CallRegisterDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(callRegisterService.findById(id));
    }

    /** Partial update — only non-null JSON fields are applied (legacy CallRegisterDao.update). */
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<CallRegisterDTO> update(@PathVariable Long id, @RequestBody CallRegisterUpdateRequest body) {
        return ResponseEntity.ok(callRegisterService.update(id, body));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        callRegisterService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
