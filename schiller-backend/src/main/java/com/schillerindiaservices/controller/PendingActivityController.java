package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.PendingActivityDTO;
import com.schillerindiaservices.dto.PendingActivityRequest;
import com.schillerindiaservices.service.PendingActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.schillerindiaservices.security.SecurityRoleUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pending-activity")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class PendingActivityController {

    private final PendingActivityService pendingActivityService;

    @GetMapping("/pending")
    public ResponseEntity<Page<PendingActivityDTO>> getPending(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort,
            Authentication authentication
    ) {
        boolean allDivisions = SecurityRoleUtils.isVpOperationalScope(authentication);
        return ResponseEntity.ok(
                pendingActivityService.findPendingForUser(
                        authentication.getName(),
                        allDivisions,
                        division,
                        search,
                        pageable(page, size, sort)
                )
        );
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<PendingActivityDTO>> getCompleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id,desc") String sort,
            Authentication authentication
    ) {
        boolean isPrivileged = authentication.getAuthorities().stream().anyMatch(a -> {
            String auth = a.getAuthority();
            return "ROLE_ADMIN".equalsIgnoreCase(auth) || "ROLE_VICE_CHANCELLOR".equalsIgnoreCase(auth);
        });
        return ResponseEntity.ok(
                pendingActivityService.findCompletedForUser(
                        authentication.getName(),
                        isPrivileged,
                        division,
                        search,
                        pageable(page, size, sort)
                )
        );
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<PendingActivityDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pendingActivityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PendingActivityDTO> create(
            @RequestBody PendingActivityRequest body,
            Authentication authentication
    ) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equalsIgnoreCase(a.getAuthority()));
        return ResponseEntity.ok(pendingActivityService.create(body, authentication.getName(), isAdmin));
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<PendingActivityDTO> update(
            @PathVariable Long id,
            @RequestBody PendingActivityRequest body
    ) {
        return ResponseEntity.ok(pendingActivityService.update(id, body));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pendingActivityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private static Pageable pageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        String prop = sortParams[0].isBlank() ? "id" : sortParams[0];
        return PageRequest.of(page, size, Sort.by(direction, prop));
    }
}
