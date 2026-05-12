package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.SpareMasterDTO;
import com.schillerindiaservices.dto.SpareMasterRequest;
import com.schillerindiaservices.service.SpareMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spares")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class SpareMasterController {

    private final SpareMasterService spareMasterService;

    @GetMapping("/pending")
    public ResponseEntity<Page<SpareMasterDTO>> getPending(
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
                spareMasterService.findPendingForUser(
                        authentication.getName(),
                        isPrivileged,
                        division,
                        search,
                        pageable(page, size, sort)
                )
        );
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<SpareMasterDTO>> getCompleted(
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
                spareMasterService.findCompletedForUser(
                        authentication.getName(),
                        isPrivileged,
                        division,
                        search,
                        pageable(page, size, sort)
                )
        );
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<SpareMasterDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(spareMasterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SpareMasterDTO> create(
            @RequestBody SpareMasterRequest body,
            Authentication authentication
    ) {
        return ResponseEntity.ok(spareMasterService.create(authentication.getName(), body));
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<SpareMasterDTO> update(
            @PathVariable Long id,
            @RequestBody SpareMasterRequest body
    ) {
        return ResponseEntity.ok(spareMasterService.update(id, body));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        spareMasterService.deleteById(id);
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
