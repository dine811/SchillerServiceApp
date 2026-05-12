package com.schillerindiaservices.controller;

import com.schillerindiaservices.dto.EmployeeDTO;
import com.schillerindiaservices.dto.EmployeeRequestDTO;
import com.schillerindiaservices.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for Employee master data.
 *
 * Base path: /api/employees
 *
 * GET    /api/employees                  – paginated list of all employees
 * GET    /api/employees/active           – all active employees (no pagination)
 * GET    /api/employees/{id}             – get one by id
 * GET    /api/employees/by-username?u=.. – get one by username
 * GET    /api/employees/by-role?role=..  – paginated by role
 * GET    /api/employees/by-division?d=.. – paginated by division
 * POST   /api/employees                  – create new employee
 * PUT    /api/employees/{id}             – full update
 * PATCH  /api/employees/{id}/toggle-active – flip active flag
 * DELETE /api/employees/{id}             – delete
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // ─── GET all (paginated) ─────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAll(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20, sort = "empId") Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(employeeService.searchEmployees(search, pageable));
        }
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    // ─── GET active employees (for dropdowns) ───────────────────────────────────
    @GetMapping("/active")
    public ResponseEntity<List<EmployeeDTO>> getActive() {
        return ResponseEntity.ok(employeeService.findAllActive());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<EmployeeDTO>> getByRegion(@PathVariable Long regionId) {
        return ResponseEntity.ok(employeeService.findByRegionId(regionId));
    }

    // ─── GET by ID ───────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    // ─── GET by username ─────────────────────────────────────────────────────────
    @GetMapping("/by-username")
    public ResponseEntity<EmployeeDTO> getByUsername(@RequestParam("u") String username) {
        return ResponseEntity.ok(employeeService.findByUsername(username));
    }

    // ─── GET by role (Engineer, Admin, FQC …) ───────────────────────────────────
    @GetMapping("/by-role")
    public ResponseEntity<Page<EmployeeDTO>> getByRole(
            @RequestParam String role,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(employeeService.findByRole(role, pageable));
    }

    // ─── GET by division ─────────────────────────────────────────────────────────
    @GetMapping("/by-division")
    public ResponseEntity<Page<EmployeeDTO>> getByDivision(
            @RequestParam("d") String division,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(employeeService.findByDivision(division, pageable));
    }

    // ─── POST create ─────────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.create(request));
    }

    // ─── PUT update ──────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(employeeService.update(id, request));
    }

    // ─── PATCH toggle active ─────────────────────────────────────────────────────
    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<EmployeeDTO> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.toggleActive(id));
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
